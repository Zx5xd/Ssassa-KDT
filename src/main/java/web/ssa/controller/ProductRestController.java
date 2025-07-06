package web.ssa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import web.ssa.dto.PageResponse;
import web.ssa.dto.products.ProductDTO;
import web.ssa.dto.products.SimpleProductDTO;
import web.ssa.entity.products.ProductImg;
import web.ssa.entity.products.ProductMaster;
import web.ssa.service.products.ProductService;
import web.ssa.service.categories.CategoryFieldServImpl;
import web.ssa.service.categories.CategoryServiceImpl;
import web.ssa.dto.categories.CategoryFieldsDTO;
import web.ssa.dto.categories.PLCategoryDTO;
import web.ssa.service.products.ProductServiceImpl;
import web.ssa.util.DTOUtil;
import web.ssa.mapper.ConvertToDTO;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.LinkedHashSet;

@RestController
@RequestMapping("/pdr/*")
public class ProductRestController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private CategoryFieldServImpl categoryFieldServ;

    private final DTOUtil dtoUtil = new DTOUtil();

    @GetMapping("list")
    public PageResponse<SimpleProductDTO> listProducts(@RequestParam(value = "cid", defaultValue = "-1") int cid,
            Pageable pageable,
            @RequestParam(value = "search", required = false) String search) {
        Page<ProductMaster> page = cid == -1 ? this.productService.findByCategoryId(cid, pageable) : null;

        if (search != null && !search.trim().isEmpty()) {
            // 검색어가 있는 경우
            page = productServiceImpl.searchProducts(search.trim(), pageable.getPageNumber(),
                    pageable.getPageSize());
        } else if (cid != -1) {
            // 카테고리 필터링
            page = productServiceImpl.getPagedProductsByCategory(cid, pageable.getPageNumber(),
                    pageable.getPageSize());
        } else {
            // 전체 상품
            page = productServiceImpl.getPagedProducts(pageable.getPageNumber() - 1, pageable.getPageSize());
        }

        List<SimpleProductDTO> dtoList = page.getContent().stream().map(SimpleProductDTO::from).toList();

        return new PageResponse<>(
                dtoList,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements());
    }

    // Ajax 카테고리별 상품 및 필터 데이터 로드
    @GetMapping("list/ajax")
    public ResponseEntity<Map<String, Object>> loadCategoryData(
            @RequestParam(value="cid") int cid,
            @RequestParam(value="childId", required = false) Integer childId

    ) {
        try {
            Map<String, Object> response = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();

            // 카테고리별 상품 데이터 로드
            Pageable pageable = PageRequest.of(0, 30); // 첫 페이지, 30개씩
            Map<Integer, PLCategoryDTO> categoryMap = categoryService.getCategoryMap();

            Page<ProductMaster> page = this.productService.findByCategoryId(cid, pageable);

            if (cid != -1)
                if (categoryMap.get(cid).variants() != null) {
                    String subCategoryJson = mapper.writeValueAsString(categoryMap.get(cid).variants());
                    System.out.println("[ list ] subCategoryJson: " + subCategoryJson);
                    response.put("subCategoryJson", subCategoryJson);
                }

            if(childId != null) {
                page = productService.findByCategoryChildId(childId, pageable);
            }
            List<SimpleProductDTO> products = page.getContent().stream().map(SimpleProductDTO::from).toList();

            // 카테고리 필터 데이터 로드
            List<CategoryFieldsDTO> categoryFieldsDTOList = categoryFieldServ.getCategoryFieldsByCategoryId(cid);
            if (childId != null) {
                categoryFieldsDTOList = categoryFieldServ.getCategoryFieldsByChildId(cid, childId);
            }
            Map<String, List<String>> fieldFilter = dtoUtil
                    .processCategoryFieldsForFilterAsSimpleMap(categoryFieldsDTOList);

            response.put("products", products);
            response.put("catFilter", fieldFilter);
            response.put("totalCount", page.getTotalElements());
            response.put("currentPage", page.getNumber());
            response.put("totalPages", page.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "카테고리 데이터 로드 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // 동적 필터 상품 검색 API
    @GetMapping("search/filter")
    public ResponseEntity<Map<String, Object>> searchProductsByFilter(
            @RequestParam(required = false) MultiValueMap<String, String> params) {
        try {
            // params가 null이거나 비어있으면 빈 결과 반환
            if (params == null || params.isEmpty()) {
                System.out.println("params is null or empty");
                Map<String, Object> response = new HashMap<>();
                response.put("products", List.of());
                response.put("productKeys", List.of());
                response.put("totalCount", 0);
                return ResponseEntity.ok(response);
            }

            // params: {RAM=[8GB,16GB], 저장공간=[512GB], cid=[1], ...}
            Map<String, List<String>> filterMap = new HashMap<>(params);

            // cid가 있으면 카테고리 필터링을 위해 별도 처리
            Integer categoryId = null;
            if (filterMap.containsKey("cid") && !filterMap.get("cid").isEmpty()) {
                try {
                    categoryId = Integer.parseInt(filterMap.get("cid").get(0));
                    filterMap.remove("cid"); // cid는 필터에서 제외
                } catch (NumberFormatException e) {
                    // cid 파싱 실패 시 무시
                }
            }

            List<ProductMaster> products;
            if (categoryId != null) {
                // 특정 카테고리 내에서 필터링 (임시로 전체 검색 후 필터링)
                List<ProductMaster> allProducts = productService.searchByDynamicFilter(filterMap);
                final int finalCategoryId = categoryId;
                products = allProducts.stream()
                        .filter(product -> product.getCategoryId() == finalCategoryId)
                        .toList();
            } else {
                // 전체 상품에서 필터링
                products = productService.searchByDynamicFilter(filterMap);
            }

            List<ProductDTO> productDTOs = ConvertToDTO.productDTOList(products);

            // 동적으로 출력할 key 최대 5개 추출
            Set<String> keySet = new LinkedHashSet<>();
            for (ProductDTO p : productDTOs) {
                if (p.getDetail() != null)
                    keySet.addAll(p.getDetail().keySet());
                if (keySet.size() >= 5)
                    break;
            }
            List<String> productKeys = keySet.stream().limit(5).toList();

            Map<String, Object> response = new HashMap<>();
            response.put("products", productDTOs);
            response.put("productKeys", productKeys);
            response.put("totalCount", productDTOs.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "상품 검색 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("img/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) {
        ProductImg productImg = this.productService.findByImgId(id);
        if (productImg == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            String imageUrl = "https://web.hyproz.myds.me/ssa_shop/img/" + productImg.getImgPath();
            URL url = new URL(imageUrl);
            InputStream in = url.openStream();
            byte[] imageBytes = StreamUtils.copyToByteArray(in);

            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("image/webp"))
                    .body(imageBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

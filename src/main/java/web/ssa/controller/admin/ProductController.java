package web.ssa.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import web.ssa.cache.CategoriesCache;
import web.ssa.cache.ProductImgCache;
import web.ssa.dto.products.ProductCreateDTO;
import web.ssa.dto.products.ProductDTO;
import web.ssa.dto.products.ProductVariantDTO;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductReview;
import web.ssa.entity.products.ProductVariant;
import web.ssa.mapper.ConvertToDTO;
import web.ssa.service.products.ProductReviewServImpl;
import web.ssa.service.products.ProductService;
import web.ssa.service.products.ProductServiceImpl;
import web.ssa.service.products.ProductVariantService;
import web.ssa.util.FormatUtil;
import web.ssa.service.WebDAVService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/pd")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductServiceImpl pdServImpl;
    private final ProductReviewServImpl pdReviewServImpl;
    private final ProductVariantService productVariantService;
    private final CategoriesCache categoriesCache;
    private final ProductImgCache productImgCache;
    private final AdminController adminController;
    private final WebDAVService webDAVService;

    // 상품 목록 페이지 (카테고리별 필터링 지원)
    @GetMapping("/get/products")
    public String listProducts(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model,
            HttpSession session) {
        int pageSize = 20;

        Page<ProductMaster> productPage;

        if (search != null && !search.trim().isEmpty()) {
            // 검색어가 있는 경우
            productPage = productService.searchProducts(search.trim(), page - 1, pageSize);
        } else if (categoryId != null) {
            // 카테고리 필터링
            productPage = productService.getPagedProductsByCategory(categoryId, page - 1, pageSize);
        } else {
            // 전체 상품
            productPage = productService.getPagedProducts(page - 1, pageSize);
        }

        model.addAttribute("category", categoriesCache.getCachedCategories());
        model.addAttribute("productPage", productPage);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("searchKeyword", search);
        model.addAttribute("formatUtil", new FormatUtil());
        model.addAttribute("productVariantService", productVariantService);

        if (adminController.isAdmin(session)) {
            return "admin/productList";
        } else {
            return "shop/productList";
        }
    }

    // 상품 삭제 (amount를 -1로 설정)
    @PostMapping("/set/product/delete/{id}")
    public String deleteProduct(
            @PathVariable("id") int id,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "page", required = false) Integer page,
            HttpSession session) {
        // amount를 -1로 설정하여 논리적 삭제
        productService.softDeleteProduct(id);

        String redirectUrl = "pd/get/products";
        if (categoryId != null) {
            redirectUrl += "?categoryId=" + categoryId;
        }
        if (page != null) {
            redirectUrl += (categoryId != null ? "&" : "?") + "page=" + page;
        }

        if (adminController.isAdmin(session)) {
            return "redirect:" + redirectUrl;
        } else {
            return "redirect:/login";
        }
    }

    // 상품 등록 페이지
    @GetMapping("/set/product/new")
    public String newProductForm(Model model, HttpSession session) {
        model.addAttribute("category", categoriesCache.getCachedCategories());
        if (adminController.isAdmin(session)) {
            return "admin/productForm";
        } else {
            return "redirect:/login";
        }
    }

    // 상품 등록 처리
    @PostMapping("/set/product/create")
    public String createProduct(@ModelAttribute ProductCreateDTO cp,
            @RequestParam(value = "simpleImg", required = false) MultipartFile simpleImg,
            @RequestParam(value = "detailImg", required = false) MultipartFile detailImg,
            HttpSession session) {
        if (!adminController.isAdmin(session)) {
            return "redirect:/login";
        }

        cp.setCategoryId(Integer.parseInt(cp.getStrCategoryId()));

        if (cp.getStrCategoryChildId().isEmpty()) {
            cp.setCategoryChildId(0);
        } else {
            cp.setCategoryChildId(Integer.parseInt(cp.getStrCategoryChildId()));
        }

        System.out.println("cp.getRegistrationType() : " + cp.getRegistrationType());

        // 등록 방식에 따른 처리
        if ("multiple".equals(cp.getRegistrationType())) {

            cp.getVariants().forEach(variant -> {
                System.out.println("variant.toString() : " + variant.toString());
            });

            // 여러개 등록 모드
            // amount, detailImg, detail 필드는 무시하고 variants에서 처리
            cp.setAmount(0); // 기본값 설정
            cp.setDetail("{}"); // 기본값 설정 (빈 JSON 객체)

            // variants가 null이면 빈 리스트로 초기화
            if (cp.getVariants() == null) {
                cp.setVariants(new java.util.ArrayList<>());
            }

            // WebDAV를 통한 메인 상품 이미지 업로드
            try {
                if (simpleImg != null && !simpleImg.isEmpty()) {
                    String simpleImgUrl = webDAVService.uploadFile(simpleImg);
                    String simpleImgFileName = simpleImgUrl.substring(simpleImgUrl.lastIndexOf("/") + 1);
                    cp.setSimpleImgFileName(simpleImgFileName);
                }

                // 상품 변형들의 상세 이미지 업로드
                if (cp.getVariants() != null && !cp.getVariants().isEmpty()) {
                    List<ProductVariantDTO> variants = new ArrayList<>();
                    for (ProductVariantDTO variant : cp.getVariants()) {
                        System.out.println("[ 상품 변형들의 상세 이미지 업로드 ] variant : " + variant.toString());
                        if (variant != null && variant.getDetailImgFile() != null
                                && !variant.getDetailImgFile().isEmpty()) {
                            System.out.println("[ 상품 변형들의 상세 이미지 업로드 ] detailImgFile : "
                                    + variant.getDetailImgFile().getOriginalFilename());
                            String detailImgUrl = webDAVService.uploadFile(variant.getDetailImgFile());
                            System.out.println("[ 상품 변형들의 상세 이미지 업로드 ] detailImgUrl : " + detailImgUrl);
                            String detailImgFileName = detailImgUrl.substring(detailImgUrl.lastIndexOf("/") + 1);
                            System.out.println("[ 상품 변형들의 상세 이미지 업로드 ] detailImgFileName : " + detailImgFileName);
                            variant.setDetailImgFileName(detailImgFileName);
                        }
                        variants.add(variant);
                    }
                    cp.setVariants(variants);
                }

                // 상품 저장 (변형 포함)
                productService.saveProductWithVariants(cp);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 단개 등록 모드 (기존 로직)
            try {
                if (simpleImg != null && !simpleImg.isEmpty()) {
                    String simpleImgUrl = webDAVService.uploadFile(simpleImg);
                    String simpleImgFileName = simpleImgUrl.substring(simpleImgUrl.lastIndexOf("/") + 1);
                    cp.setSimpleImgFileName(simpleImgFileName);
                }

                if (detailImg != null && !detailImg.isEmpty()) {
                    String detailImgUrl = webDAVService.uploadFile(detailImg);
                    String detailImgFileName = detailImgUrl.substring(detailImgUrl.lastIndexOf("/") + 1);
                    cp.setDetailImgFileName(detailImgFileName);
                }

                // 상품 저장
                productService.saveProduct(cp);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/pd/get/products";
    }

    // 상품 수정 페이지
    @GetMapping("/set/product/edit/{id}")
    public String editProductForm(@PathVariable("id") int id, Model model, HttpSession session) {
        if (!adminController.isAdmin(session)) {
            return "redirect:/login";
        }

        ProductDTO product = ConvertToDTO.productToDTO(this.productService.getProductById(id));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // price가 0이거나 detailImg가 0일 때만 변형들 가져오기
        List<ProductVariant> existingVariants = null;
        if (product.getPrice() == 0 || product.getDetailImg() == 0) {
            existingVariants = productVariantService.getVariantByMasterId(id);
        }

        model.addAttribute("regStr", product.getReg() != null ? sdf.format(product.getReg()) : "");
        model.addAttribute("product", product);
        model.addAttribute("category", categoriesCache.getCachedCategories());
        model.addAttribute("productImgCache", this.productImgCache);
        model.addAttribute("existingVariants", existingVariants);

        return "admin/productEditForm";
    }

    @PostMapping("/set/product/update/{id}")
    public String updateProduct(@PathVariable("id") int id, @ModelAttribute ProductCreateDTO cp,
            @RequestParam(value = "simpleImg", required = false) MultipartFile simpleImg,
            @RequestParam(value = "detailImg", required = false) MultipartFile detailImg,
            HttpSession session) {
        if (!adminController.isAdmin(session)) {
            return "redirect:/login";
        }

        ProductDTO originalProduct = ConvertToDTO.productToDTO(this.productService.getProductById(id));

        cp.setCategoryId(Integer.parseInt(cp.getStrCategoryId()));

        if (cp.getStrCategoryChildId().isEmpty()) {
            cp.setCategoryChildId(0);
        } else {
            cp.setCategoryChildId(Integer.parseInt(cp.getStrCategoryChildId()));
        }

        // price가 0이거나 detailImg가 0일 때만 변형 처리
        if (cp.getPrice() == 0 || originalProduct.getDetailImg() == 0) {
            // variants가 null이면 빈 리스트로 초기화
            if (cp.getVariants() == null) {
                cp.setVariants(new java.util.ArrayList<>());
            }

            try {
                // WebDAV를 통한 메인 상품 이미지 업로드
                if (simpleImg != null && !simpleImg.isEmpty()) {
                    String simpleImgUrl = webDAVService.uploadFile(simpleImg);
                    String simpleImgFileName = simpleImgUrl.substring(simpleImgUrl.lastIndexOf("/") + 1);
                    cp.setSimpleImgFileName(simpleImgFileName);
                }

                // 상품 변형들의 상세 이미지 업로드
                if (cp.getVariants() != null && !cp.getVariants().isEmpty()) {
                    List<ProductVariantDTO> variants = new ArrayList<>();
                    for (ProductVariantDTO variant : cp.getVariants()) {
                        if (variant != null) {
                            if (variant.getExistingDetailImg() != null && variant.getExistingDetailImg() != 0) {
                                variant.setDetailImg(variant.getExistingDetailImg());
                            } else if (variant.getDetailImgFile() != null
                                    && !variant.getDetailImgFile().isEmpty()) {
                                String detailImgUrl = webDAVService.uploadFile(variant.getDetailImgFile());
                                String detailImgFileName = detailImgUrl.substring(detailImgUrl.lastIndexOf("/") + 1);
                                variant.setDetailImgFileName(detailImgFileName);
                            }
                        }
                        variants.add(variant);
                    }
                    cp.setVariants(variants);
                }

                // 상품 수정 (변형 포함)
                productService.updateProductWithVariants(id, cp, originalProduct);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 일반 상품 수정 (기존 로직)
            this.productService.updateProduct(id, cp, originalProduct);
        }

        return "redirect:/pd/get/products";
    }

    // 상품 리뷰 목록 (API)
    @GetMapping("/{productId}/reviews")
    @ResponseBody
    public Page<ProductReview> listReviews(
            @PathVariable("productId") int productId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        return pdReviewServImpl.getPagedReviews(productId, page, size);
    }

    // ManageMent

}
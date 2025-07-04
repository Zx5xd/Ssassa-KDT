package web.ssa.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.ssa.cache.CategoriesCache;
import web.ssa.dto.categories.CategoryFieldsDTO;
import web.ssa.dto.categories.DisplayOrderDTO;
import web.ssa.dto.products.ProductDTO;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.service.categories.CategoryChildServImpl;
import web.ssa.service.categories.CategoryFieldServ;
import web.ssa.service.categories.CategoryFieldServImpl;
import web.ssa.service.categories.CategoryServiceImpl;
import web.ssa.service.products.ProductService;
import web.ssa.entity.member.User;
import web.ssa.entity.products.ProductMaster;
import web.ssa.util.DTOUtil;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.HashMap;

@Controller
@RequestMapping("/cat/*")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private CategoryChildServImpl categoryChildServ;

    @Autowired
    private CategoryFieldServImpl categoryFieldServ;

    @Autowired
    private final CategoriesCache categoriesCache;

    @Autowired
    private ProductService productService;

    private final DTOUtil dtoUtil = new DTOUtil();

    public CategoryController(CategoriesCache categoriesCache) {
        this.categoriesCache = categoriesCache;
    }

    // 공통: 관리자 권한 확인 메서드
    private boolean isAdmin(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        return loginUser != null && "ADMIN".equals(loginUser.getRole());
    }

    // Management
    @GetMapping("displayOrder")
    public String displayOrder() {
        return "admin/reDisplayorder";
    }

    @GetMapping("/displayOrder/{categoryId}")
    public String displayOrder(@PathVariable("categoryId") int categoryId, Model model) {
        List<CategoriesChild> categoryChildren = categoriesCache.getCategoryChildren(categoryId);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryChildren", categoryChildren);
        // 필요시 필드/attributeKey 등도 추가
        return "admin/category/reDisplayorder";
    }

    @PostMapping("set/displayOrder-static")
    public String reorder(@ModelAttribute DisplayOrderDTO orderDTO, Model model) {
        try {
            System.out.println("orderDTO : " + orderDTO);

            categoryFieldServ.reorderField(orderDTO);
            model.addAttribute("resultMsg", "순서 변경 완료");
        } catch (Exception e) {
            String msg = "[ 순서 변경 실패 ] " + e.getMessage();
            model.addAttribute("resultMsg", msg);
        }
        return "redirect:/cat/displayOrder/" + orderDTO.getCategoryId();
    }

    @GetMapping("displayOrder-All")
    public String displayOrderAll(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) {

        int categoryId = 1;

        if (req.getParameter("categoryId") != null) {
            categoryId = Integer.parseInt(req.getParameter("categoryId"));
        }

        List<CategoryFieldsDTO> dtoList = this.categoryFieldServ.getCategoryFieldsByCategoryId(categoryId);

        model.addAttribute("dtoList", dtoList);
        model.addAttribute("category", categoriesCache.getCachedCategories());

        return "admin/category/reDisplayorderAll";
    }

    @GetMapping("displayOrder-All/{id}")
    public String displayOrderAllById(Model model, @PathVariable("id") int id) {

        model.addAttribute("category", categoriesCache.getCachedCategories());
        model.addAttribute("dtoList", this.categoryFieldServ.getCatFieldsByCategoryId(id));

        return "admin/category/reDisplayorderAll";
    }

    @PostMapping("set/displayOrder-All")
    public String allReorder(@RequestParam("fieldId") List<Integer> fieldId,
            @RequestParam("orderNum") List<Integer> orderNum,
            @RequestParam("categoryId") int categoryId,
            Model model) {
        try {
            this.categoryFieldServ.allReorder(fieldId, orderNum);
            model.addAttribute("resultMsg", "순서 변경 완료");
        } catch (Exception e) {
            String msg = "[ 순서 변경 실패 ] " + e.getMessage();
            model.addAttribute("resultMsg", msg);
        }
        return "redirect:/cat/displayOrder-All/" + categoryId;
    }

    // 하위 카테고리 목록 조회 API
    @GetMapping("api/categories/{categoryId}/children")
    @ResponseBody
    public List<CategoriesChild> getCategoryChildren(@PathVariable("categoryId") int categoryId) {
        Categories category = categoriesCache.getCachedCategories().stream()
                .filter(cat -> cat.getId() == categoryId)
                .findFirst()
                .orElse(null);

        if (category != null) {
            return categoryChildServ.getCategoryChild(category);
        }
        return List.of();
    }

    // 속성 키 목록 조회 API
    @GetMapping("api/categories/{categoryId}/attributes")
    @ResponseBody
    public List<String> getAttributeKeys(@PathVariable("categoryId") int categoryId,
            @RequestParam(value = "childId", required = false) Integer childId,
            @RequestParam(value = "fieldId", required = false) Integer fieldId) {
        List<CategoryFieldsDTO> fields;
        if (childId != null) {
            Categories category = categoriesCache.getCachedCategories().stream()
                    .filter(cat -> cat.getId() == categoryId)
                    .findFirst()
                    .orElse(null);
            if (category != null) {
                List<CategoriesChild> children = categoryChildServ.getCategoryChild(category);
                fields = categoryFieldServ.getCategoryFieldsByChildId(categoryId, children);
            } else {
                fields = List.of();
            }
        } else {
            fields = categoryFieldServ.getCategoryFieldsByCategoryId(categoryId);
        }

        return fields.stream()
                .map(CategoryFieldsDTO::getAttributeKey)
                .distinct()
                .toList();
    }

    // 필드 정보 조회 API
    @GetMapping("/fields/{categoryId}")
    @ResponseBody
    public List<CategoryFieldsDTO> getCategoryFields(@PathVariable("categoryId") int categoryId,
            @RequestParam(value = "childId", required = false) Integer childId) {
        List<CategoryFieldsDTO> fields;
        if (childId != null) {
            Categories category = categoriesCache.getCachedCategories().stream()
                    .filter(cat -> cat.getId() == categoryId)
                    .findFirst()
                    .orElse(null);
            if (category != null) {
                List<CategoriesChild> children = categoryChildServ.getCategoryChild(category);
                fields = categoryFieldServ.getCategoryFieldsByChildId(categoryId, children);
            } else {
                fields = List.of();
            }
        } else {
            fields = categoryFieldServ.getCategoryFieldsByCategoryId(categoryId);
        }

        return fields;
    }

    // valueList 편집 페이지
    @GetMapping("/edit/valueList/{fieldId}")
    public String editValueList(@PathVariable("fieldId") int fieldId, Model model) {
        try {
            // 필드 정보 조회
            CategoryFieldsDTO field = categoryFieldServ.getCategoryFieldById(fieldId);
            if (field == null) {
                model.addAttribute("errorMsg", "필드를 찾을 수 없습니다.");
                return "redirect:/cat/displayOrder-All";
            }

            // valueList를 Map으로 변환
            Map<String, String> valueListMap = dtoUtil.valueListToMap(field.getValueList());

            model.addAttribute("field", field);
            model.addAttribute("valueListMap", valueListMap);

            return "admin/category/valueListEdit";
        } catch (Exception e) {
            model.addAttribute("errorMsg", "오류가 발생했습니다: " + e.getMessage());
            return "redirect:/cat/displayOrder-All";
        }
    }

    // valueList 업데이트
    @PostMapping("/update/valueList")
    public String updateValueList(@RequestParam("fieldId") int fieldId,
            @RequestParam("values") List<String> values,
            @RequestParam("weights") List<String> weights,
            Model model) {
        try {
            // 입력값 검증
            if (values.size() != weights.size()) {
                model.addAttribute("errorMsg", "값과 가중치의 개수가 일치하지 않습니다.");
                return "redirect:/cat/edit/valueList/" + fieldId;
            }

            // JSON 배열 생성
            StringBuilder jsonBuilder = new StringBuilder("[");
            for (int i = 0; i < values.size(); i++) {
                if (i > 0)
                    jsonBuilder.append(",");
                jsonBuilder.append("{\"value\":\"").append(values.get(i)).append("\",\"weight\":")
                        .append(weights.get(i)).append("}");
            }
            jsonBuilder.append("]");

            String newValueList = jsonBuilder.toString();

            // 데이터베이스 업데이트
            categoryFieldServ.updateValueList(fieldId, newValueList);

            // 필드 정보 조회하여 categoryId 가져오기
            CategoryFieldsDTO field = categoryFieldServ.getCategoryFieldById(fieldId);
            int categoryId = field != null ? field.getCategoryId() : 1;

            model.addAttribute("resultMsg", "값 목록이 성공적으로 업데이트되었습니다.");
            return "redirect:/cat/displayOrder-All/" + categoryId;
        } catch (Exception e) {
            model.addAttribute("errorMsg", "업데이트 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/cat/edit/valueList/" + fieldId;
        }
    }

    // Field명 | ValueList 테이블 테스트
    @GetMapping("/fieldFilter")
    public String setFilter(Model model, @RequestParam(value = "categoryId", defaultValue = "1") Integer categoryId,
            @RequestParam(value = "childId", required = false) Integer childId) {
        try {
            // CategoryFields 데이터 조회 (카테고리 ID 1번 기준)
            List<CategoryFieldsDTO> categoryFieldsDTOList = categoryFieldServ.getCategoryFieldsByCategoryId(categoryId);
            if (childId != null) {
                categoryFieldsDTOList = categoryFieldServ.getCategoryFieldsByChildId(categoryId, childId);
            }

            // 단순 Map 형태 (값만)
            Map<String, List<String>> fieldFilter = dtoUtil
                    .processCategoryFieldsForFilterAsSimpleMap(categoryFieldsDTOList);
            model.addAttribute("filter", fieldFilter);

//            categoryFieldServ.setFilter(categoryId);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("테스트 중 오류 발생: " + e.getMessage());
            model.addAttribute("errorMsg", "테스트 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "admin/category/test/testSimpleTable";
    }

    // 동적 필터 AJAX 상품 검색
    @GetMapping("/searchProductsAjax")
    public String searchProductsAjax(@RequestParam MultiValueMap<String, String> params, Model model) {
        // params: {RAM=[8GB,16GB], 저장공간=[512GB], ...}
        Map<String, List<String>> filterMap = new HashMap<>(params);
        List<ProductMaster> products = productService.searchByDynamicFilter(filterMap);
        List<ProductDTO> productDTOs = web.ssa.mapper.ConvertToDTO.productDTOList(products);
        // 동적으로 출력할 key 최대 5개 추출
        Set<String> keySet = new LinkedHashSet<>();
        for (ProductDTO p : productDTOs) {
            if (p.getDetail() != null)
                keySet.addAll(p.getDetail().keySet());
            if (keySet.size() >= 5)
                break;
        }
        List<String> productKeys = keySet.stream().limit(5).toList();
        model.addAttribute("products", productDTOs);
        model.addAttribute("productKeys", productKeys);
        return "admin/category/test/productListPartial";
    }
}

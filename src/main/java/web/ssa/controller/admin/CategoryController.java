package web.ssa.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.ssa.cache.CategoriesCache;
import web.ssa.dto.categories.CategoryFieldsDTO;
import web.ssa.dto.categories.DisplayOrderDTO;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.service.categories.CategoryChildServImpl;
import web.ssa.service.categories.CategoryServiceImpl;
import web.ssa.entity.member.User;
import web.ssa.util.DTOUtil;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cat/*")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private CategoryChildServImpl categoryChildServ;

    @Autowired
    private final CategoriesCache categoriesCache;

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
        return "admin/reDisplayorder";
    }

    @PostMapping("set/displayOrder-static")
    public String reorder(@ModelAttribute DisplayOrderDTO orderDTO, Model model) {
        try {
            System.out.println("orderDTO : " + orderDTO);

            categoryService.reorderField(orderDTO);
            model.addAttribute("resultMsg", "순서 변경 완료");
        } catch (Exception e) {
            String msg = "[ 순서 변경 실패 ] " + e.getMessage();
            model.addAttribute("resultMsg", msg);
        }
        return "admin/reDisplayorder";
    }

    @GetMapping("displayOrder-All")
    public String displayOrderAll(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) {

        int categoryId = 1;

        if (req.getParameter("categoryId") != null) {
            categoryId = Integer.parseInt(req.getParameter("categoryId"));
        }

        List<CategoryFieldsDTO> dtoList = this.categoryService.getCategoryFieldsByCategoryId(categoryId);

        model.addAttribute("dtoList", dtoList);
        model.addAttribute("category", categoriesCache.getCachedCategories());

        return "admin/reDisplayorderAll";
    }

    @GetMapping("displayOrder-All/{id}")
    public String displayOrderAllById(Model model, @PathVariable("id") int id) {

        Categories category = new Categories();
        List<CategoriesChild> child = null;
        if (id == 5 || id == 8 || id == 9) {
            category = this.categoriesCache.getCachedCategories().stream()
                    .filter(cat -> cat.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));

            child = this.categoryChildServ.getCategoryChild(category);
        }
        System.out.println("id : " + id);
        List<CategoryFieldsDTO> dtoList = switch (id) {
            case 5, 8, 9 ->
                this.categoryService.getCategoryFieldsByChildId(id,
                        child);
            default -> this.categoryService.getCategoryFieldsByCategoryId(id);
        };
        dtoList.forEach(dto -> {
            System.out.println("dto.getAttributeKey() : " + dto.getAttributeKey());
        });

        model.addAttribute("category", categoriesCache.getCachedCategories());
        model.addAttribute("dtoList", dtoList);

        return "admin/reDisplayorderAll";
    }

    @PostMapping("set/displayOrder-All")
    public String allReorder(@RequestParam List<Integer> fieldId,
            @RequestParam List<Integer> orderNum,
            Model model) {
        try {
            this.categoryService.allReorder(fieldId, orderNum);
            model.addAttribute("resultMsg", "순서 변경 완료");
        } catch (Exception e) {
            String msg = "[ 순서 변경 실패 ] " + e.getMessage();
            model.addAttribute("resultMsg", msg);
        }
        return "admin/reDisplayorderAll";
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
                fields = categoryService.getCategoryFieldsByChildId(categoryId, children);
            } else {
                fields = List.of();
            }
        } else {
            fields = categoryService.getCategoryFieldsByCategoryId(categoryId);
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
                fields = categoryService.getCategoryFieldsByChildId(categoryId, children);
            } else {
                fields = List.of();
            }
        } else {
            fields = categoryService.getCategoryFieldsByCategoryId(categoryId);
        }

        return fields;
    }

    @GetMapping("/test/vl")
    public void testVl() {
        System.out.println("---------------- testVL ----------------");

        // 테스트용 valueList 데이터
        String testValueList = "[{\"value\": \"422\", \"weight\": 1}, {\"value\": \"N/A\", \"weight\": 2}, {\"value\": \"am\", \"weight\": 3}, {\"value\": \"남양키친플라워\", \"weight\": 4}, {\"value\": \"대웅모닝컴\", \"weight\": 5}, {\"value\": \"디디오랩\", \"weight\": 6}, {\"value\": \"디바인바이오\", \"weight\": 7}, {\"value\": \"디온리\", \"weight\": 8}, {\"value\": \"레이나\", \"weight\": 9}, {\"value\": \"롯데하이마트\", \"weight\": 10}, {\"value\": \"리비에라앤바\", \"weight\": 11}, {\"value\": \"리빙코리아\", \"weight\": 12}, {\"value\": \"리하스\", \"weight\": 13}, {\"value\": \"마이디어\", \"weight\": 14}, {\"value\": \"보랄\", \"weight\": 15}, {\"value\": \"보만\", \"weight\": 16}, {\"value\": \"보토코리아\", \"weight\": 17}, {\"value\": \"샤크닌자\", \"weight\": 18}, {\"value\": \"씨엔컴퍼니\", \"weight\": 19}, {\"value\": \"아이닉\", \"weight\": 20}, {\"value\": \"오아\", \"weight\": 21}, {\"value\": \"오쿠\", \"weight\": 22}, {\"value\": \"자일렉\", \"weight\": 23}, {\"value\": \"재원전자\", \"weight\": 24}, {\"value\": \"중산물산\", \"weight\": 25}, {\"value\": \"코렐브랜드\", \"weight\": 26}, {\"value\": \"쿠첸\", \"weight\": 27}, {\"value\": \"쿠쿠전자\", \"weight\": 28}, {\"value\": \"키친아트\", \"weight\": 29}, {\"value\": \"테팔\", \"weight\": 30}, {\"value\": \"풀무원건강생활\", \"weight\": 31}, {\"value\": \"필립스\", \"weight\": 32}, {\"value\": \"한경희생활과학\", \"weight\": 33}]";

        System.out.println("=== 테스트용 valueList 데이터 ===");
        System.out.println(testValueList);
        System.out.println();

        System.out.println("=== valueListToMap 결과 ===");
        Map<String, String> valueListMap = dtoUtil.valueListToMap(testValueList);
        valueListMap.forEach((key, value) -> {
            System.out.println("key: " + key + " → value: " + value);
        });
        System.out.println();

        System.out.println("=== valueListToSortedMap 결과 (weight 순 정렬) ===");
        Map<String, String> sortedValueListMap = dtoUtil.valueListToSortedMap(testValueList);
        sortedValueListMap.forEach((key, value) -> {
            System.out.println("key: " + key + " → value: " + value);
        });
        System.out.println();

        // 기존 데이터베이스 데이터도 테스트
        System.out.println("=== 데이터베이스 데이터 테스트 ===");
        List<CategoryFieldsDTO> fields = this.categoryService.getCategoryFieldsByCategoryId(1);
        for (CategoryFieldsDTO field : fields) {
            if (field.getValueList() != null && !field.getValueList().trim().isEmpty()) {
                System.out.println("--- " + field.getAttributeKey() + " ---");
                Map<String, String> valueLists = dtoUtil.valueListToMap(field.getValueList());
                valueLists.forEach((key, value) -> {
                    System.out.println("key: " + key + " → value: " + value);
                });
                System.out.println();
            }
        }
    }

    // valueList 편집 페이지
    @GetMapping("/edit/valueList/{fieldId}")
    public String editValueList(@PathVariable("fieldId") int fieldId, Model model) {
        try {
            // 필드 정보 조회
            CategoryFieldsDTO field = categoryService.getCategoryFieldById(fieldId);
            if (field == null) {
                model.addAttribute("errorMsg", "필드를 찾을 수 없습니다.");
                return "admin/reDisplayorderAll";
            }

            // valueList를 Map으로 변환
            Map<String, String> valueListMap = dtoUtil.valueListToMap(field.getValueList());

            model.addAttribute("field", field);
            model.addAttribute("valueListMap", valueListMap);

            return "admin/valueListEdit";
        } catch (Exception e) {
            model.addAttribute("errorMsg", "오류가 발생했습니다: " + e.getMessage());
            return "admin/reDisplayorderAll";
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
            categoryService.updateValueList(fieldId, newValueList);

            // 필드 정보 조회하여 categoryId 가져오기
            CategoryFieldsDTO field = categoryService.getCategoryFieldById(fieldId);
            int categoryId = field != null ? field.getCategoryId() : 1;

            model.addAttribute("resultMsg", "값 목록이 성공적으로 업데이트되었습니다.");
            return "redirect:/cat/displayOrder-All/" + categoryId;
        } catch (Exception e) {
            model.addAttribute("errorMsg", "업데이트 중 오류가 발생했습니다: " + e.getMessage());
            return "redirect:/cat/edit/valueList/" + fieldId;
        }
    }
}

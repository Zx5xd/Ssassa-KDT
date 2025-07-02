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
}

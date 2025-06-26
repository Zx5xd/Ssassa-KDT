package web.ssa.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.ssa.cache.CategoriesCache;
import web.ssa.dto.categories.CategoryFieldsDTO;
import web.ssa.dto.categories.DisplayOrderDTO;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.entity.categories.CategoryFields;
import web.ssa.enumf.CategoryType;
import web.ssa.service.categories.CategoryChildServImpl;
import web.ssa.service.categories.CategoryServiceImpl;

import java.util.ArrayList;
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

    @GetMapping("fridge")
    public String fridge() {
        return "fridge";
    }

    @GetMapping("pcProducts")
    public String computer() {
        return "computer";
    }

    @GetMapping("phone")
    public String phone() {
        return "phone";
    }

    @GetMapping("tv")
    public String tv() {
        return "tv";
    }

    @GetMapping("washer")
    public String washer() {
        return "washer";
    }

    @GetMapping("dryer")
    public String dryer() {
        return "dryer";
    }

    @GetMapping("speaker")
    public String speaker() {
        return "speaker";
    }

    // Management
    @GetMapping("displayOrder")
    public String displayOrder() {
        return "reDisplayorder";
    }

    @PostMapping("set/displayOrder-static")
    public String reorder(@RequestBody Map<String, Object> payload, Model model) {
        try {

            DisplayOrderDTO orderDTO = new DisplayOrderDTO(
                    Integer.parseInt(payload.get("categoryId").toString()),
                    Integer.parseInt(payload.get("childId").toString()),
                    payload.get("attributeKey").toString(),
                    Integer.parseInt(payload.get("oldOrder").toString()),
                    Integer.parseInt(payload.get("newOrder").toString())
            );

            categoryService.reorderField(orderDTO);
            model.addAttribute("resultMsg","순서 변경 완료");
        } catch (Exception e) {
            String msg = "[ 순서 변경 실패 ] " + e.getMessage();
            model.addAttribute("resultMsg",msg);
        }
        return "reDisplayorder";
    }

    @GetMapping("displayOrder-All")
    public String displayOrderAll(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) {

        int categoryId = 1;

        if(req.getParameter("categoryId") != null) {
            categoryId = Integer.parseInt(req.getParameter("categoryId"));
        }

        List<CategoryFieldsDTO> dtoList = this.categoryService.getCategoryFieldsByCategoryId(categoryId);

        model.addAttribute("dtoList", dtoList);
        model.addAttribute("category", categoriesCache.getCachedCategories());

        return "reDisplayorderAll";
    }

    @GetMapping("displayOrder-All/{id}")
    public String displayOrderById(Model model, @PathVariable int id) {

        Categories category = new Categories();
        List<CategoriesChild> child = null;
        if (id == 5 || id == 8 || id == 9) {
           category  = this.categoriesCache.getCachedCategories().stream()
                    .filter(cat -> cat.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));

           child = this.categoryChildServ.getCategoryChild(category);
        }

        List<CategoryFieldsDTO> dtoList = switch (id) {
            case 5, 8, 9 ->
                    this.categoryService.getCategoryFieldsByChildId(id,
                   child);
            default -> this.categoryService.getCategoryFieldsByCategoryId(id);
        };

        model.addAttribute("category", categoriesCache.getCachedCategories());
        model.addAttribute("dtoList", dtoList);

        return "reDisplayorderAll";
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
            model.addAttribute("resultMsg",msg);
        }
        return "reDisplayorderAll";
    }

}

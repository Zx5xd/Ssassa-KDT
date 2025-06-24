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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import web.ssa.cache.CategoriesCache;
import web.ssa.dto.categories.CategoryFieldsDTO;
import web.ssa.dto.categories.DisplayOrderDTO;
import web.ssa.entity.categories.CategoryFields;
import web.ssa.enumf.CategoryType;
import web.ssa.service.categories.CategoryServiceImpl;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/cat/*")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

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
    public ResponseEntity<?> reorder(@RequestBody Map<String, Object> payload) {
        try {

            DisplayOrderDTO orderDTO = new DisplayOrderDTO(
                    Integer.parseInt(payload.get("categoryId").toString()),
                    Integer.parseInt(payload.get("childId").toString()),
                    payload.get("attributeKey").toString(),
                    Integer.parseInt(payload.get("oldOrder").toString()),
                    Integer.parseInt(payload.get("newOrder").toString())
            );

            categoryService.reorderField(orderDTO);

            return ResponseEntity.ok("순서 변경 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("에러: " + e.getMessage());
        }
    }

    @GetMapping("displayOrder-All")
    public String displayOrderAll(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) {

        int categoryId = 1;

        if(req.getParameter("categoryId") != null) {
            categoryId = Integer.parseInt(req.getParameter("categoryId"));
        }

        List<CategoryFieldsDTO> dtoList = this.categoryService.getCategoryFieldsByCategoryId(categoryId);
        model.addAttribute("dtoList", dtoList);
//        model.addAttribute("category", categoriesCache);
        categoriesCache.getCachedCategories().forEach(categories -> {
            System.out.println(categories.toString());
        });
        return "reDisplayorderAll";
    }

    @PostMapping("set/displayOrder-All")
    public ResponseEntity<?> allReorder(@RequestBody Map<String, Object> payload) {
        try {
            DisplayOrderDTO orderDTO = new DisplayOrderDTO(
                    Integer.parseInt(payload.get("categoryId").toString()),
                    Integer.parseInt(payload.get("childId").toString()),
                    payload.get("attributeKey").toString(),
                    Integer.parseInt(payload.get("oldOrder").toString()),
                    Integer.parseInt(payload.get("newOrder").toString())
            );

            return ResponseEntity.ok("순서 변경 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("에러: " + e.getMessage());
        }
    }

}

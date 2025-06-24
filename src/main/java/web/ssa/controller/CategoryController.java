package web.ssa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.ssa.service.categories.CategoryChildServImpl;
import web.ssa.service.categories.CategoryServiceImpl;

import java.util.Map;


@Controller
@RequestMapping("/cat/*")
public class CategoryController {
    /*    @GetMapping("/main")
    public String home() {

        return "main"; // → /WEB-INF/views/home.jsp로 포워딩됨
    }

    @PostMapping("/main")
    public String postHome(Model model, HttpSession session, HttpServletRequest request) {

        return "main"; // → /WEB-INF/views/home.jsp로 포워딩됨
    }*/

    private CategoryServiceImpl categoryService;

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

    @PostMapping ("set/displayOrder-static")
    public ResponseEntity<?> reorder(@RequestBody Map<String, Object> payload) {
        try {
            int categoryId = Integer.parseInt(payload.get("categoryId").toString());
            int childId = Integer.parseInt(payload.get("childId").toString());
            String attributeKey = payload.get("attributeKey").toString();
            int oldOrder = Integer.parseInt(payload.get("oldOrder").toString());
            int newOrder = Integer.parseInt(payload.get("newOrder").toString());

            categoryService.reorderField(categoryId, childId, attributeKey, oldOrder, newOrder);
            return ResponseEntity.ok("순서 변경 완료");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("에러: " + e.getMessage());
        }
    }

}

package web.ssa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/cat/*")
public class CategoryController {
    /*    @GetMapping("/main")
    public String home() {

        return "main"; // → /WEB-INF/views/home.jsp로 포워딩됨
    }

    @PostMapping("/main")
    public String postHome(Model model, HttpSession session, HttpServletRequest request) {

        return "main"; // → /WEB-INF/views/home.jsp로 포워딩됨
    }*/

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

}

package web.ssa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/")
public class MainController {

    @GetMapping("/main")
    public String home() {
        return "shop/main"; // → /WEB-INF/views/shop/main.jsp로 포워딩됨
    }

}

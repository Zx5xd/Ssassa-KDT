package web.ssa.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.ssa.entity.member.User;

@Controller
public class TestController {

    @GetMapping("/test")
    public String showMainPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");

        if (user != null) {
            model.addAttribute("nickname", user.getNickname());
        }

        return "test";

    }
}

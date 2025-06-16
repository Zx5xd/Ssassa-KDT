package web.ssa.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import web.ssa.entity.member.User;
import web.ssa.service.member.MemberService;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final MemberService memberService; //

    @GetMapping("/mypage")
    public String showMypage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "mypage";
    }

    @PostMapping("/withdraw")
    public String withdraw(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/login";
        }

        memberService.deleteUser(user.getEmail());
        session.invalidate();
        return "redirect:/login";
    }
}

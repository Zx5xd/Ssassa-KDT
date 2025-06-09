package web.ssa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.ssa.dto.member.MemberDTO;
import web.ssa.service.member.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController { // ✅ @RequestMapping 없음

    private final MemberService memberService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "register"; // /WEB-INF/views/register.jsp
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("memberDTO") MemberDTO dto, Model model) {
        try {
            memberService.register(dto);
            return "redirect:/login"; // 성공 시 로그인 화면 등으로 이동
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}

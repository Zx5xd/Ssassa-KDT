package web.ssa.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.ssa.entity.member.User;
import web.ssa.service.member.MemberService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String showLoginPage(@CookieValue(value = "rememberEmail", required = false) String rememberedEmail,
                                Model model) {
        model.addAttribute("rememberedEmail", rememberedEmail);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rememberEmail", required = false) String rememberEmail,
                        HttpSession session,
                        HttpServletResponse response,
                        Model model) {

        User user = memberService.login(email, password);

        if (user == null || user.isDeleted()) {
            model.addAttribute("error", "로그인 실패 또는 탈퇴한 계정입니다.");
            return "login";
        }

        // ✅ 세션 등록 (1시간 유지)
        session.setAttribute("loginUser", user);
        session.setMaxInactiveInterval(60 * 60);

        // ✅ loginToken 발급 및 저장
        String token = UUID.randomUUID().toString();
        user.setLoginToken(token);
        user.setLoginTokenCreatedAt(LocalDateTime.now());
        memberService.save(user);

        // ✅ loginToken 쿠키 설정 (1시간)
        Cookie autoLoginCookie = new Cookie("loginToken", token);
        autoLoginCookie.setMaxAge(60 * 60);
        autoLoginCookie.setPath("/");
        autoLoginCookie.setHttpOnly(true);
        response.addCookie(autoLoginCookie);

        // ✅ rememberEmail 쿠키 처리 (1일 유지)
        if ("on".equals(rememberEmail)) {
            Cookie rememberCookie = new Cookie("rememberEmail", email);
            rememberCookie.setMaxAge(60 * 60 * 24 * 1);
            rememberCookie.setPath("/");
            response.addCookie(rememberCookie);
        } else {
            Cookie rememberCookie = new Cookie("rememberEmail", null);
            rememberCookie.setMaxAge(0);
            rememberCookie.setPath("/");
            response.addCookie(rememberCookie);
        }

        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("loginUser");
            session.invalidate();
        }

        if (user != null) {
            user.clearLoginToken();
            memberService.save(user);
        }

        // ✅ loginToken 쿠키 제거
        Cookie autoLoginCookie = new Cookie("loginToken", null);
        autoLoginCookie.setMaxAge(0);
        autoLoginCookie.setPath("/");
        autoLoginCookie.setHttpOnly(true);
        response.addCookie(autoLoginCookie);

        return "redirect:/login";
    }

    @GetMapping("/find-id")
    public String showFindIdForm() {
        return "findId";
    }

    @PostMapping("/find-id")
    public String findId(@RequestParam String name,
                         @RequestParam String phone,
                         Model model) {
        Optional<User> userOpt = memberService.findByNameAndPhone(name, phone);
        if (userOpt.isPresent()) {
            model.addAttribute("foundEmail", userOpt.get().getEmail());
        } else {
            model.addAttribute("error", "일치하는 회원 정보를 찾을 수 없습니다.");
        }
        return "findId";
    }

    @GetMapping("/find-password")
    public String showFindPasswordForm() {
        return "findPassword";
    }

    @PostMapping("/find-password")
    public String findPassword(@RequestParam String email,
                               @RequestParam String phone,
                               Model model) {
        Optional<User> userOpt = memberService.findByEmailAndPhone(email, phone);
        if (userOpt.isPresent()) {
            model.addAttribute("email", email);
            return "resetPassword";
        } else {
            model.addAttribute("error", "일치하는 회원 정보를 찾을 수 없습니다.");
            return "findPassword";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword,
                                Model model) {
        memberService.updatePassword(email, newPassword);
        model.addAttribute("message", "비밀번호가 성공적으로 변경되었습니다. 로그인해 주세요.");
        return "login";
    }
}

package web.ssa.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rememberEmail", required = false) String rememberEmail,
                        HttpServletResponse response,
                        HttpServletRequest request,
                        HttpSession session,
                        Model model) {

        try {
            User user = memberService.login(email, password);
            if (user == null || user.isDeleted()) {
                throw new IllegalStateException("로그인 실패 또는 탈퇴한 계정입니다.");
            }

            session.setAttribute("loginUser", user);

            // 자동 로그인 쿠키 저장
            String token = UUID.randomUUID().toString();
            Cookie autoLoginCookie = new Cookie("loginToken", token);
            autoLoginCookie.setMaxAge(60 * 5); // 5분
            autoLoginCookie.setPath("/");
            autoLoginCookie.setHttpOnly(true);
            response.addCookie(autoLoginCookie);

            memberService.saveTokenWithTimestamp(email, token, LocalDateTime.now());

            // 이메일 기억 쿠키 처리
            Cookie rememberCookie = new Cookie("rememberEmail",
                    "on".equals(rememberEmail) ? email : null);
            rememberCookie.setMaxAge("on".equals(rememberEmail) ? 60 * 60 * 24 * 30 : 0);
            rememberCookie.setPath("/");
            response.addCookie(rememberCookie);

            // ✅ 로그인 성공 후 경로 설정
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin";
            } else {
                return "redirect:/shop/products";  // ✅ 쇼핑몰 상품 목록으로 이동
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         HttpSession session) {

        User user = (User) session.getAttribute("loginUser");
        session.invalidate();

        Cookie autoLoginCookie = new Cookie("loginToken", null);
        autoLoginCookie.setMaxAge(0);
        autoLoginCookie.setPath("/");
        response.addCookie(autoLoginCookie);

        if (user != null) {
            memberService.clearLoginToken(user.getEmail());
        }

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
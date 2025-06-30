package web.ssa.controller.client;

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

    // 로그인 폼
    @GetMapping("/login")
    public String showLoginPage(HttpServletRequest request, Model model) {
        for (Cookie cookie : Optional.ofNullable(request.getCookies()).orElse(new Cookie[0])) {
            if ("rememberEmail".equals(cookie.getName())) {
                model.addAttribute("rememberedEmail", cookie.getValue());
            }
        }
        return "client/login";
    }

    // 로그인 처리
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

            // 세션에 로그인 정보 저장
            session.setAttribute("loginUser", user);

            // 자동 로그인 토큰 쿠키 생성
            String token = UUID.randomUUID().toString();
            Cookie autoLoginCookie = new Cookie("loginToken", token);
            autoLoginCookie.setMaxAge(60 * 5); // 5분
            autoLoginCookie.setPath("/");
            autoLoginCookie.setHttpOnly(true);
            response.addCookie(autoLoginCookie);

            memberService.saveTokenWithTimestamp(email, token, LocalDateTime.now());

            // 이메일 저장 쿠키
            Cookie rememberCookie = new Cookie("rememberEmail",
                    "on".equals(rememberEmail) ? email : null);
            rememberCookie.setMaxAge("on".equals(rememberEmail) ? 60 * 60 * 24 * 30 : 0);
            rememberCookie.setPath("/");
            response.addCookie(rememberCookie);

            // 관리자 여부에 따라 리디렉션
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin";
            } else {
                return "redirect:/index";
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "client/login";
        }
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session) {

        User user = (User) session.getAttribute("loginUser");
        session.invalidate();

        // 자동 로그인 쿠키 삭제
        Cookie autoLoginCookie = new Cookie("loginToken", null);
        autoLoginCookie.setMaxAge(0);
        autoLoginCookie.setPath("/");
        response.addCookie(autoLoginCookie);

        if (user != null) {
            memberService.clearLoginToken(user.getEmail());
        }

        return "redirect:/login";
    }

    // 아이디 찾기 폼
    @GetMapping("/find-id")
    public String showFindIdForm() {
        return "client/findId";
    }

    // 아이디 찾기 처리
    @PostMapping("/find-id")
    public String findId(@RequestParam("name") String name,
            @RequestParam("phone") String phone,
            Model model) {

        Optional<User> userOpt = memberService.findByNameAndPhone(name, phone);
        if (userOpt.isPresent()) {
            model.addAttribute("foundEmail", userOpt.get().getEmail());
        } else {
            model.addAttribute("error", "일치하는 회원 정보를 찾을 수 없습니다.");
        }

        return "client/findId";
    }

    // 비밀번호 찾기 폼
    @GetMapping("/find-password")
    public String showFindPasswordForm() {
        return "client/findPassword";
    }

    // 비밀번호 찾기 처리
    @PostMapping("/find-password")
    public String findPassword(@RequestParam("email") String email,
            @RequestParam("phone") String phone,
            Model model) {

        Optional<User> userOpt = memberService.findByEmailAndPhone(email, phone);
        if (userOpt.isPresent()) {
            model.addAttribute("email", email);
            return "client/resetPassword";
        } else {
            model.addAttribute("error", "일치하는 회원 정보를 찾을 수 없습니다.");
            return "client/findPassword";
        }
    }

    // 비밀번호 재설정
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email,
            @RequestParam("newPassword") String newPassword,
            Model model) {

        memberService.updatePassword(email, newPassword);
        model.addAttribute("message", "비밀번호가 성공적으로 변경되었습니다. 로그인해 주세요.");
        return "client/login";
    }
}

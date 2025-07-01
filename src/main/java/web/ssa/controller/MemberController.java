package web.ssa.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.ssa.dto.member.MemberDTO;
import web.ssa.service.member.MemberService;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Autowired
    private JavaMailSender mailSender;

    // 이메일 인증 정보 저장소 (인증 코드와 발급 시간)
    private final Map<String, EmailAuth> emailAuthMap = new ConcurrentHashMap<>();

    // 회원가입 폼 요청
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(
            @RequestParam("email") String email,
            @RequestParam("code") String code,
            @ModelAttribute("memberDTO") MemberDTO dto,
            Model model) {

        dto.setEmail(email);

        // 인증 코드 유효성 검사
        EmailAuth auth = emailAuthMap.get(email);
        if (!isValidCode(auth, code)) {
            model.addAttribute("error", "이메일 인증을 먼저 완료하거나 인증 코드가 일치하지 않습니다. (유효시간 5분)");
            return "register";
        }

        dto.setEmailVerified(true);
        dto.setEmailVerifiedAt(LocalDateTime.now());

        try {
            memberService.register(dto);
            emailAuthMap.remove(email); // 인증 기록 삭제
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    // 이메일 인증 코드 발송
    @GetMapping("/send-auth-code")
    @ResponseBody
    public String sendAuthCode(@RequestParam("email") String email) {
        if (memberService.existsByEmail(email)) {
            return "이미 가입된 이메일입니다.";
        }

        SecureRandom random = new SecureRandom();
        String code = String.format("%06d", random.nextInt(1000000)); // 000000 ~ 999999

        EmailAuth auth = new EmailAuth();
        auth.setCode(code);
        auth.setCreatedAt(LocalDateTime.now());
        emailAuthMap.put(email, auth);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("회원가입 인증 코드");
        message.setText("인증 코드는: " + code + "\n(5분 이내에 입력해주세요)");

        try {
            mailSender.send(message);
        } catch (Exception e) {
            return "메일 전송에 실패했습니다. 다시 시도해주세요.";
        }

        return "인증 코드가 이메일로 전송되었습니다.";
    }

    // 인증 코드 확인
    @GetMapping("/verify-code")
    @ResponseBody
    public boolean verifyCode(@RequestParam("email") String email,
                              @RequestParam("code") String code) {
        EmailAuth auth = emailAuthMap.get(email);
        return isValidCode(auth, code);
    }

    // 닉네임 중복 확인
    @GetMapping("/check-nickname")
    @ResponseBody
    public String checkNickname(@RequestParam("nickname") String nickname) {
        boolean exists = memberService.existsByUsername(nickname);
        return String.valueOf(exists); // "true" or "false"
    }

    // 이메일 인증 코드 유효성 검사 (공통 메소드)
    private boolean isValidCode(EmailAuth auth, String code) {
        if (auth == null) return false;
        if (auth.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) return false;
        return auth.getCode().equals(code);
    }

    // 이메일 인증 정보 클래스
    @Data
    static class EmailAuth {
        private String code;
        private LocalDateTime createdAt;
    }
}

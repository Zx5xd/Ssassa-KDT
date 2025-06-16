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

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Autowired
    private JavaMailSender mailSender;

    // ✅ 인증 정보 저장 (이메일 → 인증 정보 객체)
    private final Map<String, EmailAuth> emailAuthMap = new ConcurrentHashMap<>();

    // ✅ 회원가입 폼 요청
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "register";
    }

    // ✅ 회원가입 처리
    @PostMapping("/register")
    public String register(@ModelAttribute("memberDTO") MemberDTO dto, Model model) {
        EmailAuth auth = emailAuthMap.get(dto.getEmail());

        // 인증 여부와 유효 시간 확인
        if (auth == null || auth.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "이메일 인증을 먼저 완료해주세요. (유효시간 5분)");
            return "register";
        }

        dto.setEmailVerified(true);
        dto.setEmailVerifiedAt(LocalDateTime.now());

        try {
            memberService.register(dto);
            emailAuthMap.remove(dto.getEmail()); // 인증 정보 제거
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    // ✅ 이메일 인증 코드 발송
    @GetMapping("/send-auth-code")
    @ResponseBody
    public String sendAuthCode(@RequestParam("email") String email) {
        if (memberService.existsByEmail(email)) {
            return "이미 가입된 이메일입니다.";
        }

        // 인증 코드 생성 (6자리)
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);

        // 저장
        EmailAuth auth = new EmailAuth();
        auth.setCode(code);
        auth.setCreatedAt(LocalDateTime.now());
        emailAuthMap.put(email, auth);

        // 이메일 발송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("회원가입 인증 코드");
        message.setText("인증 코드는: " + code + "\n(5분 이내에 입력해주세요)");
        mailSender.send(message);

        return "인증 코드가 이메일로 전송되었습니다.";
    }

    // ✅ 이메일 인증 확인
    @GetMapping("/verify-code")
    @ResponseBody
    public boolean verifyCode(@RequestParam("email") String email,
                              @RequestParam("code") String code) {

        EmailAuth auth = emailAuthMap.get(email);

        if (auth == null) return false;
        if (auth.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) return false;

        return code.equals(auth.getCode());
    }

    // ✅ 인증 코드 + 발급시간 클래스
    @Data
    static class EmailAuth {
        private String code;
        private LocalDateTime createdAt;
    }

    @GetMapping("/check-nickname")
    @ResponseBody
    public String checkNickname(@RequestParam("nickname") String nickname) {
        boolean exists = memberService.existsByUsername(nickname);
        return String.valueOf(exists); // "true" or "false"
    }
}

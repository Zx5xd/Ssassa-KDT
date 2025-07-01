package web.ssa.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.ssa.entity.member.User;
import web.ssa.service.member.MemberService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final JavaMailSender mailSender;

    //  관리자 메인 화면
    @GetMapping("/admin")
    public String adminPage(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (!isAdmin(session)) return "redirect:/login";

        model.addAttribute("adminName", loginUser.getName());
        return "admin/admin";
    }

    // 관리자 권한 확인
    private boolean isAdmin(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        return loginUser != null && "ADMIN".equals(loginUser.getRole());
    }

    // 상품 목록
    @GetMapping("/admin/products")
    public String manageProducts(HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        return "admin/productList";
    }

    // 문의사항/환불
    @GetMapping("/admin/inquiries")
    public String manageInquiries(HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        return "admin/inquiryList";
    }

    // 전체 회원 목록 - 정상 / 탈퇴 유저 나눠서 출력
    @GetMapping("/admin/users")
    public String manageUsers(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        List<User> activeUsers = memberService.findActiveUsers();    // 정상 유저
        List<User> deletedUsers = memberService.findDeletedUsers();  // 탈퇴 유저

        model.addAttribute("activeUsers", activeUsers);
        model.addAttribute("deletedUsers", deletedUsers);
        return "admin/userList";
    }

    // 회원 수정 화면
    @GetMapping("/admin/editUser")
    public String editUserForm(@RequestParam String email, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        Optional<User> userOpt = memberService.findById(email);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "admin/editUser";
        }
        return "redirect:/admin/users";
    }

    // 회원 수정 처리 (비밀번호는 제외)
    @PostMapping("/admin/updateUser")
    public String updateUser(@ModelAttribute User user, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        // 기존 사용자 정보 유지 (비밀번호 덮어쓰기 방지)
        Optional<User> existingUserOpt = memberService.findById(user.getEmail());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(user.getName());
            existingUser.setNickname(user.getNickname());
            existingUser.setPhone(user.getPhone());
            memberService.updateUser(existingUser);
        }
        return "redirect:/admin/users";
    }

    // 회원 삭제 (관리자 권한)
    @GetMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam String email, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        memberService.deleteUserByAdmin(email);
        return "redirect:/admin/users";
    }

    // 탈퇴 회원 복구
    @GetMapping("/admin/restoreUser")
    public String restoreUser(@RequestParam String email, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        memberService.restoreUser(email);
        return "redirect:/admin/users";
    }

    // 닉네임 인라인 수정
    @PostMapping("/admin/updateUserInline")
    public String updateUserInline(@RequestParam String email,
                                   @RequestParam String nickname,
                                   HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        Optional<User> userOpt = memberService.findById(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setNickname(nickname);
            memberService.updateUser(user);
        }
        return "redirect:/admin/users";
    }

    // 임시 비밀번호 전송
    @PostMapping("/admin/sendTempPassword")
    public String sendTempPassword(@RequestParam String email, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        Optional<User> userOpt = memberService.findById(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // 임시 비밀번호 생성
            String tempPassword = UUID.randomUUID().toString().substring(0, 10);

            // 비밀번호 업데이트 (암호화 필요 시 암호화 적용)
            user.setPassword(tempPassword); // 실제 서비스: passwordEncoder.encode(tempPassword)
            memberService.updateUser(user);

            // 이메일 발송
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("임시 비밀번호 안내");
            message.setText("안녕하세요.\n\n임시 비밀번호는 아래와 같습니다:\n\n"
                    + tempPassword + "\n\n로그인 후 반드시 비밀번호를 변경해 주세요.");
            mailSender.send(message);
        }

        return "redirect:/admin/users";
    }
}

package web.ssa.controller.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import web.ssa.cache.CategoriesCache;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.member.User;
import web.ssa.service.InquiryService;
import web.ssa.service.KakaoPayService;
import web.ssa.service.member.MemberService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final KakaoPayService kakaoPayService;
    private final InquiryService inquiryService;
    private final MemberService memberService;
    private final JavaMailSender mailSender;
    private final CategoriesCache categoriesCache;

    // 공통: 관리자 권한 확인 메서드
    private boolean isAdmin(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        return loginUser != null && "ADMIN".equals(loginUser.getRole());
    }

    // 관리자 메인 페이지 (메뉴 선택 화면)
    @GetMapping("")
    public String adminMainPage(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        model.addAttribute("adminName", loginUser.getName());
        return "admin/admin"; // /WEB-INF/views/admin/admin.jsp
    }

    // 환불 + 문의사항 목록 페이지
    @GetMapping("/refunds")
    public String showAdminDashboard(HttpSession session, Model model) {
        if (!isAdmin(session))
            return "redirect:/login";
        model.addAttribute("refunds", kakaoPayService.getPendingRefunds());
        model.addAttribute("inquiries", inquiryService.getAll());
        return "admin/adminRefunds"; // 수정 완료
    }

    // 환불 승인 처리
    @PostMapping("/refund/complete")
    public String completeRefund(@RequestParam("paymentId") Long paymentId) {
        kakaoPayService.completeRefund(paymentId);
        return "redirect:/admin/refunds";
    }

    // 문의사항 삭제
    @PostMapping("/inquiry/delete")
    public String deleteInquiry(@RequestParam("id") Long id) {
        inquiryService.delete(id);
        return "redirect:/admin/refunds";
    }

    // 상품 등록 관리 페이지
    @GetMapping("/products")
    public String manageProducts(HttpSession session, Model model) {
        if (!isAdmin(session))
            return "redirect:/login";
        return "admin/productList"; // /WEB-INF/views/admin/productList.jsp
    }

    // 회원 목록 관리 페이지
    @GetMapping("/users")
    public String manageUsers(HttpSession session, Model model) {
        if (!isAdmin(session))
            return "redirect:/login";

        List<User> activeUsers = memberService.findActiveUsers(); // 정상 유저
        List<User> deletedUsers = memberService.findDeletedUsers(); // 탈퇴 유저

        model.addAttribute("activeUsers", activeUsers);
        model.addAttribute("deletedUsers", deletedUsers);
        return "admin/userList"; // /WEB-INF/views/admin/userList.jsp
    }

    // 회원 수정 화면
    @GetMapping("/editUser")
    public String editUserForm(@RequestParam("email") String email, HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
            return "redirect:/login";
        }

        Optional<User> userOpt = memberService.findById(email);
        if (userOpt.isEmpty()) {
            return "redirect:/admin/users";
        }

        model.addAttribute("user", userOpt.get());
        return "admin/editUser";
    }

    // 회원 수정 처리 (비밀번호는 제외)
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user, HttpSession session) {
        if (!isAdmin(session))
            return "redirect:/login";

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

    // 사용자 삭제
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("email") String email, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
            return "redirect:/login";
        }

        memberService.deleteUserByAdmin(email);
        return "redirect:/admin/users";
    }

    // 사용자 복구
    @PostMapping("/restoreUser")
    public String restoreUser(@RequestParam("email") String email, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
            return "redirect:/login";
        }

        memberService.restoreUser(email);
        return "redirect:/admin/users";
    }

    // 사용자 정보 인라인 수정
    @PostMapping("/updateUserInline")
    public String updateUserInline(@RequestParam("email") String email,
            @RequestParam("nickname") String nickname,
            HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
            return "redirect:/login";
        }

        Optional<User> userOpt = memberService.findById(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setNickname(nickname);
            memberService.updateUser(user);
        }
        return "redirect:/admin/users";
    }

    // 임시 비밀번호 발송
    @PostMapping("/sendTempPassword")
    public String sendTempPassword(@RequestParam("email") String email, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
            return "redirect:/login";
        }

        Optional<User> userOpt = memberService.findById(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // 임시 비밀번호 생성
            String tempPassword = UUID.randomUUID().toString().substring(0, 10);

            // 비밀번호 업데이트
            user.setPassword(tempPassword);
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

    // 문의사항 별도 목록 페이지 (옵션)
    @GetMapping("/inquiries")
    public String manageInquiries(HttpSession session, Model model) {
        if (!isAdmin(session))
            return "redirect:/login";
        model.addAttribute("inquiries", inquiryService.getAll());
        return "admin/inquiryList"; // /WEB-INF/views/admin/inquiryList.jsp
    }

    // 카테고리 관리 메인 페이지
    @GetMapping("/categories")
    public String showCategoryManagement(HttpSession session, Model model) {
        if (!isAdmin(session))
            return "redirect:/login";

        // 카테고리 목록을 모델에 추가
        model.addAttribute("categories", categoriesCache.getCachedCategories());
        return "admin/categoryManagement";
    }

    // 카테고리별 순서 변경 페이지 선택
    @GetMapping("/categories/{categoryId}/order")
    public String showCategoryOrderSelection(@PathVariable("categoryId") int categoryId,
            HttpSession session,
            Model model) {
        if (!isAdmin(session))
            return "redirect:/login";

        // 카테고리 정보 조회
        Categories category = categoriesCache.getCachedCategories().stream()
                .filter(cat -> cat.getId() == categoryId)
                .findFirst()
                .orElse(null);

        if (category == null) {
            model.addAttribute("error", "카테고리를 찾을 수 없습니다.");
            return "redirect:/admin/categories";
        }

        model.addAttribute("category", category);
        return "admin/categoryOrderSelection";
    }

}
package web.ssa.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.ssa.entity.member.User;
import web.ssa.service.InquiryService;
import web.ssa.service.KakaoPayService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final KakaoPayService kakaoPayService;
    private final InquiryService inquiryService;

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
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("refunds", kakaoPayService.getPendingRefunds());
        model.addAttribute("inquiries", inquiryService.getAll());
        return "admin/adminRefunds"; // ✅ 수정 완료
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
        if (!isAdmin(session)) return "redirect:/login";
        return "admin/productList"; // /WEB-INF/views/admin/productList.jsp
    }

    // 회원 목록 관리 페이지
    @GetMapping("/users")
    public String manageUsers(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        return "admin/userList"; // /WEB-INF/views/admin/userList.jsp
    }

    // 문의사항 별도 목록 페이지 (옵션)
    @GetMapping("/inquiries")
    public String manageInquiries(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("inquiries", inquiryService.getAll());
        return "admin/inquiryList"; // /WEB-INF/views/admin/inquiryList.jsp
    }
}
package web.ssa.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import web.ssa.entity.Inquiry.Inquiry;
import web.ssa.entity.member.User;
import web.ssa.service.InquiryService;
import web.ssa.service.KakaoPayService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final KakaoPayService kakaoPayService;
    private final InquiryService inquiryService;

    // ✅ 관리자 권한 체크
    private boolean isAdmin(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        return loginUser != null && "ADMIN".equals(loginUser.getRole());
    }

    // ✅ 관리자 메인 페이지
    @GetMapping("")
    public String adminMainPage(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("adminName", loginUser.getName());
        return "admin/admin";
    }

    // ✅ 환불 + 문의사항 목록 페이지
    @GetMapping("/refunds")
    public String showAdminDashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        model.addAttribute("refunds", kakaoPayService.getPendingRefunds());
        model.addAttribute("inquiries", inquiryService.getAllInquiries());

        return "admin/adminRefunds";
    }

    // ✅ 환불 승인 처리
    @PostMapping("/refund/complete")
    public String completeRefund(@RequestParam("paymentId") Long paymentId) {
        kakaoPayService.completeRefund(paymentId);
        return "redirect:/admin/refunds";
    }

    // ✅ 문의사항 삭제 처리
    @PostMapping("/inquiry/delete")
    public String deleteInquiry(@RequestParam("id") Long id) {
        inquiryService.deleteInquiry(id);
        return "redirect:/admin/refunds";
    }

    // ✅ 관리자 전용 문의 목록 (기존 유지)
    @GetMapping("/inquiries")
    public String adminInquiryList(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("inquiries", inquiryService.getAllInquiries());
        return "admin/inquiryList";
    }

    // ✅ 문의 상세 보기 (추가된 기능)
    @GetMapping("/inquiry/detail/{id}")
    public String adminInquiryDetail(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        // 기존 목록 유지하면서 선택된 문의 추가
        model.addAttribute("refunds", kakaoPayService.getPendingRefunds());
        model.addAttribute("inquiries", inquiryService.getAllInquiries());
        model.addAttribute("selectedInquiry", inquiryService.getInquiryById(id));

        return "admin/adminRefunds"; // 같은 페이지에서 상세보기
    }

    // ✅ 답변 등록 처리 (추가된 기능)
    @PostMapping("/inquiry/reply")
    public String replyToInquiry(@RequestParam("id") Long id,
                                 @RequestParam("adminComment") String adminComment,
                                 HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";

        Inquiry inquiry = inquiryService.getInquiryById(id);
        if (inquiry != null) {
            inquiry.setAdminComment(adminComment);
            inquiry.setHasReply(true);
            inquiry.setStatus("ANSWERED");
            inquiryService.saveInquiry(inquiry);
        }

        return "redirect:/admin/inquiry/detail/" + id;
    }
}
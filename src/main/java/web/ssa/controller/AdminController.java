package web.ssa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.ssa.service.KakaoPayService;
import web.ssa.service.InquiryService;
import web.ssa.entity.Inquiry.Inquiry;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final KakaoPayService kakaoPayService;
    private final InquiryService inquiryService;

    // 관리자 페이지: 환불 + 문의사항 조회
    @GetMapping("/refunds")
    public String showAdminDashboard(Model model) {
        model.addAttribute("refunds", kakaoPayService.getPendingRefunds());
        model.addAttribute("inquiries", inquiryService.getAll());
        return "adminRefunds";
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
}
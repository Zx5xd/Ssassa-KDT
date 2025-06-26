package web.ssa.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import web.ssa.entity.Payment;
import web.ssa.entity.member.User;
import web.ssa.service.KakaoPayService;
import web.ssa.service.member.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MypageController {

    private final MemberService memberService;
    private final KakaoPayService kakaoPayService; // 결제 서비스 추가

    @GetMapping("/mypage")
    public String showMypage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/login";
        }

        // 사용자 정보 추가
        model.addAttribute("user", user);

        // 해당 사용자의 결제 내역 조회 및 추가
        List<Payment> payments = kakaoPayService.getPaymentsByUser(user.getEmail());
        model.addAttribute("payments", payments);

        return "mypage";
    }

    @PostMapping("/withdraw")
    public String withdraw(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/login";
        }

        memberService.deleteUser(user.getEmail());
        session.invalidate();
        return "redirect:/login";
    }
}

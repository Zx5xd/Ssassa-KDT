package web.ssa.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.ssa.dto.KakaoPayCancelResponse;
import web.ssa.entity.Payment;
import web.ssa.entity.Product;
import web.ssa.service.KakaoPayService;
import web.ssa.entity.member.User;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PayController {

    private final KakaoPayService kakaoPayService;

    // 상품 리스트 화면
    @GetMapping("/products")
    public String products(Model model) {
        List<Object[]> products = Arrays.asList(
                new Object[]{"테스트 상품1", 1000},
                new Object[]{"테스트 상품2", 2000}
        );
        model.addAttribute("products", products);
        return "productList";
    }

    // 카카오페이 결제 준비
    @GetMapping("/pay/ready")
    public String kakaoPay(@RequestParam("productId") int productId) {
        Product product = (productId == 1)
                ? new Product(1L, "테스트 상품1", 1000)
                : new Product(2L, "테스트 상품2", 2000);

        String redirectUrl = kakaoPayService.kakaoPayReady(product);
        return "redirect:" + redirectUrl;
    }

    // 결제 성공 후 승인 처리
    @GetMapping("/pay/success")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pgToken,
                                  HttpSession session,
                                  Model model) {
        // 1. 결제 승인 후 Payment 객체 생성 (itemName, amount, status 포함)
        Payment payment = kakaoPayService.kakaoPayApprove(pgToken);

        // 2. 세션에서 로그인한 사용자 정보 가져옴
        User user = (User) session.getAttribute("loginUser");
        if (user != null) {
            // 3. userEmail 설정
            payment.setUserEmail(user.getEmail());

            // 4. 모든 정보 포함된 Payment 객체를 DB에 저장
            kakaoPayService.savePayment(payment);
        }

        model.addAttribute("payment", payment);
        return "paySuccess";
    }

        // 결제 실패 시
        @GetMapping("/pay/fail")
        public String kakaoPayFail() {
            return "payFail";
        }

    // 사용자 마이페이지
//    @GetMapping("/mypage")
//    public String mypage(Model model) {
//        model.addAttribute("payments", kakaoPayService.getAllPayments());
//        return "mypage";
//    }

    // 사용자 환불 요청
    @PostMapping("/refund")
    public String refund(@RequestParam("paymentId") Long paymentId) {
        kakaoPayService.requestRefund(paymentId);
        return "redirect:/mypage";
    }

    // 일반 환불 처리
    @PostMapping("/pay/refund")
    public String kakaoPayRefund(@RequestParam("id") Long id, Model model) {
        Payment payment = kakaoPayService.getPaymentById(id);
        if (payment == null || payment.getTid() == null) {
            model.addAttribute("message", "환불 대상 결제가 존재하지 않습니다.");
            return "error";
        }

        KakaoPayCancelResponse response = kakaoPayService.kakaoPayCancel(payment.getTid(), payment.getAmount());
        payment.setStatus("CANCELLED");
        kakaoPayService.savePayment(payment);

        model.addAttribute("cancel", response);
        return "payCancelResult";
    }
}
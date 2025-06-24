// ✅ KakaoPayService.java 전체 코드 (userEmail 저장 로직 제거 → PayController에서 처리)
package web.ssa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import web.ssa.dto.KakaoPayCancelResponse;
import web.ssa.entity.Payment;
import web.ssa.entity.Product;
import web.ssa.repository.PaymentRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoPayService {

    private final PaymentRepository paymentRepository;
    private static final String ADMIN_KEY = "KakaoAK 3abec9eb781aaddcb9e4d14a1b3f25d5";

    private String tid;
    private Product currentProduct;

    public String kakaoPayReady(Product product) {
        this.currentProduct = product;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", ADMIN_KEY);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "order1234");
        params.add("partner_user_id", "user1234");
        params.add("item_name", product.getName());
        params.add("quantity", "1");
        params.add("total_amount", String.valueOf(product.getPrice()));
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:8080/pay/success");
        params.add("cancel_url", "http://localhost:8080/pay/fail");
        params.add("fail_url", "http://localhost:8080/pay/fail");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity("https://kapi.kakao.com/v1/payment/ready", request, Map.class);

        this.tid = (String) response.getBody().get("tid");
        return (String) response.getBody().get("next_redirect_pc_url");
    }

    public Payment kakaoPayApprove(String pgToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", ADMIN_KEY);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", this.tid);
        params.add("partner_order_id", "order1234");
        params.add("partner_user_id", "user1234");
        params.add("pg_token", pgToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        restTemplate.postForEntity("https://kapi.kakao.com/v1/payment/approve", request, Map.class);

        // ✅ 필드 채운 Payment 객체 반환 (DB 저장은 controller에서)
        Payment payment = new Payment();
        payment.setProductId(currentProduct.getId());
        payment.setItemName(currentProduct.getName());
        payment.setAmount(currentProduct.getPrice());
        payment.setStatus("SUCCESS");
        payment.setTid(this.tid);

        return payment;
    }

    public void requestRefund(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        payment.setStatus("REFUND_REQUEST");
        paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPendingRefunds() {
        return paymentRepository.findByStatus("REFUND_REQUEST");
    }

    public KakaoPayCancelResponse kakaoPayCancel(String tid, int amount) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", ADMIN_KEY);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", tid);
        params.add("cancel_amount", String.valueOf(amount));
        params.add("cancel_tax_free_amount", "0");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<KakaoPayCancelResponse> response = restTemplate.postForEntity(
                "https://kapi.kakao.com/v1/payment/cancel", request, KakaoPayCancelResponse.class
        );

        return response.getBody();
    }

    public void completeRefund(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        KakaoPayCancelResponse res = kakaoPayCancel(payment.getTid(), payment.getAmount());
        payment.setStatus("REFUNDED");
        paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    // ✅ 사용자 이메일로 결제 내역 조회
    public List<Payment> getPaymentsByUser(String email) {
        return paymentRepository.findByUserEmail(email);
    }
}
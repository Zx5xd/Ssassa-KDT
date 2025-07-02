package web.ssa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.ssa.entity.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // 환불 상태 조회용
    List<Payment> findByStatus(String status);

    // 마이페이지 사용자 결제 내역 조회용
    List<Payment> findByUserEmail(String userEmail);
}

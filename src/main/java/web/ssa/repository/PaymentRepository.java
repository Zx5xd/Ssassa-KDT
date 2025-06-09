package web.ssa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.ssa.entity.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStatus(String status);
}
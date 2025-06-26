package web.ssa.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String itemName;
    private int amount;
    private String status; // SUCCESS / REFUND_REQUEST / REFUNDED / CANCELLED
    private String tid;    // 카카오 결제 TID
    private String userEmail;

    private LocalDateTime createdAt = LocalDateTime.now();
}
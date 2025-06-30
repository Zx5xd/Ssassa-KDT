package web.ssa.entity.Inquiry;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "inquiry")
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();

    private String username;

    private String status = "PENDING"; // 답변 대기 or 답변 완료
    private String adminComment;       // 관리자 답변
    private String fileName;
    private String filePath;

    @Column(nullable = false)
    private boolean hasReply = false;
}
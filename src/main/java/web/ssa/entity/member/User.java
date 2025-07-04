package web.ssa.entity.member;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import web.ssa.entity.products.ProductReview;
import web.ssa.entity.products.ReviewRecommend;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String email; // 이메일 = 아이디

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true,
            columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci")
    private String nickname;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String role = "USER"; // 일반 유저 또는 관리자

    @CreationTimestamp
    private LocalDateTime createdAt;

    private boolean emailVerified = false;
    private LocalDateTime emailVerifiedAt;

    @Column(nullable = false)
    private String deleted = "N"; // "Y" = 탈퇴, "N" = 정상

    // 자동 로그인 토큰
    private String loginToken;

    // 자동 로그인 토큰 생성 시각
    private LocalDateTime loginTokenCreatedAt;

    @Column(name = "profile_image")
    private String profileImage;

    /**
     * 탈퇴 여부 확인
     */
    public boolean isDeleted() {
        return "Y".equalsIgnoreCase(this.deleted);
    }

    /**
     * 자동 로그인 토큰 유효성 검사
     * - 토큰 존재
     * - 생성 시각 기준 1시간 이내
     */
    public boolean isLoginTokenValid() {
        LocalDateTime now = LocalDateTime.now();
        return loginToken != null
                && loginTokenCreatedAt != null
                && loginTokenCreatedAt.plusHours(1).isAfter(now);
    }

    /**
     * 자동 로그인 토큰 초기화 (로그아웃 또는 만료 시)
     */
    public void clearLoginToken() {
        this.loginToken = null;
        this.loginTokenCreatedAt = null;
    }

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<ProductReview> reviewer;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<ReviewRecommend> recommender;
}

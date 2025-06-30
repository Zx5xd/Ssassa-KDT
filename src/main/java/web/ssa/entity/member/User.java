package web.ssa.entity.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci")
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

    // 탈퇴 여부 확인 메서드
    public boolean isDeleted() {
        return "Y".equalsIgnoreCase(this.deleted);
    }

    // 자동 로그인 토큰 유효성 검사 (5분 이내면 true)
    public boolean isLoginTokenValid() {
        return loginToken != null &&
                loginTokenCreatedAt != null &&
                loginTokenCreatedAt.plusMinutes(5).isAfter(LocalDateTime.now());
    }

    // 자동 로그인 토큰 삭제 메서드
    public void clearLoginToken() {
        this.loginToken = null;
        this.loginTokenCreatedAt = null;
    }

    @Column(name = "profile_image")
    private String profileImage;
}

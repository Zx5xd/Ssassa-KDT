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
@Table(name="users")
public class User {
    @Id
    @Column(nullable=false, unique = true)
    private String email;// 이메일 == 아이디

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String nickname;

    @Column(nullable = false)
    private String phone;

    @Column(nullable=false)
    private String role= "USER"; //일반유저 또는 관리자

    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime createdAt;// 회원가입 일시

    @Column(updatable=false)
    private boolean emailVerified; // 이메일 인증 여부

    private LocalDateTime emailVerifiedAt; // 인증 일시

    @Column(nullable=false)
    private String deleted="N"; //Y= 탈퇴함, N= 정상
}

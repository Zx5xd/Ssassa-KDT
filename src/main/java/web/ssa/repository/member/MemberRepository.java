package web.ssa.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import web.ssa.entity.member.User;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<User, String> {

    // 🔐 이메일로 회원 조회 (로그인 시 사용)
    Optional<User> findByEmail(String email);

    // 🔐 닉네임 중복 확인
    boolean existsByNickname(String nickname);

    // 🔐 자동 로그인 토큰으로 회원 조회 (필터에서 사용)
    Optional<User> findByLoginToken(String token);

    // 🔐 이메일 인증된 사용자 확인
    boolean existsByEmailAndEmailVerifiedTrue(String email);

    Optional<User> findByNameAndPhone(String name, String phone);

    Optional<User> findByEmailAndPhone(String email, String phone);

}

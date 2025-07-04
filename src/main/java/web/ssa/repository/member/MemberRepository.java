package web.ssa.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import web.ssa.entity.member.User;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 관련 JPA 리포지토리
 */
public interface MemberRepository extends JpaRepository<User, String> {

    /** 이메일로 회원 조회 (로그인 시 사용) */
    Optional<User> findByEmail(String email);

    /** 닉네임 중복 확인 */
    boolean existsByNickname(String nickname);

    /** 자동 로그인 토큰으로 회원 조회 */
    Optional<User> findByLoginToken(String token);

    /** 이메일 + 이메일 인증 여부 확인 */
    boolean existsByEmailAndEmailVerifiedTrue(String email);

    /** 아이디 찾기 (이름 + 전화번호) */
    Optional<User> findByNameAndPhone(String name, String phone);

    /** 비밀번호 찾기 (이메일 + 전화번호) */
    Optional<User> findByEmailAndPhone(String email, String phone);

    /** 관리자 제외한 모든 회원 (삭제 여부 무관) */
    List<User> findByRoleNot(String role);

    /** 관리자 제외 + 삭제 여부 지정 */
    List<User> findByRoleNotAndDeleted(String role, String deleted);

    /** 자동 로그인 토큰 있는 사용자 (토큰이 존재하는 사용자 리스트) */
    List<User> findByLoginTokenIsNotNull();
}
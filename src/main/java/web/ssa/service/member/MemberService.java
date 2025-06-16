package web.ssa.service.member;

import web.ssa.dto.member.MemberDTO;
import web.ssa.entity.member.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberService {

    // 🔐 회원가입 처리
    void register(MemberDTO dto);

    // 🔐 닉네임 중복 확인
    boolean existsByUsername(String username);

    // 🔐 이메일 중복 확인
    boolean existsByEmail(String email);

    // 🔐 로그인 처리
    User login(String email, String password);

    // 🔐 회원 탈퇴 처리
    void deleteUser(String email);

    // ✅ 자동 로그인 토큰 저장 (현재 시간 기준)
    void saveToken(String email, String token);

    // ✅ 자동 로그인 토큰 저장 (명시적 시간 지정)
    void saveTokenWithTimestamp(String email, String token, LocalDateTime createdAt);

    // ✅ 자동 로그인 토큰 유효성 검사
    User validateAutoLoginToken(String token);

    // ✅ 자동 로그인 토큰 삭제
    void clearLoginToken(String email);

    Optional<User> findByNameAndPhone(String name, String phone);

    Optional<User> findByEmailAndPhone(String email, String phone);
    void updatePassword(String email, String newPassword);


}

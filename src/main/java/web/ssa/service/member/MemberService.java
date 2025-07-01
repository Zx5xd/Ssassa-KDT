package web.ssa.service.member;

import web.ssa.dto.member.MemberDTO;
import web.ssa.entity.member.User;

import java.util.List;
import java.util.Optional;

/**
 * 회원 서비스 인터페이스
 * - 회원가입, 탈퇴, 관리자 기능
 * - 자동 로그인 및 세션 기반 인증 관리
 */
public interface MemberService {

    /** 회원가입 처리 */
    void register(MemberDTO dto);

    /** 닉네임 중복 확인 */
    boolean existsByUsername(String username);

    /** 이메일 중복 확인 */
    boolean existsByEmail(String email);

    /** 로그인 처리 */
    User login(String email, String password);

    /** 일반 유저: 회원 탈퇴 */
    void withdrawUser(String email);

    /** 관리자: 회원 논리 삭제 */
    void deleteUserByAdmin(String email);

    /** 관리자: 탈퇴 회원 복구 */
    void restoreUser(String email);

    /** 관리자: 일반 + 탈퇴 회원 전체 조회 (관리자 제외) */
    List<User> findAllUsersExcludingAdminAndDeleted();

    /** 관리자: 활성 사용자 목록 (deleted = N, 관리자 제외) */
    List<User> findActiveUsers();

    /** 관리자: 탈퇴 사용자 목록 (deleted = Y, 관리자 제외) */
    List<User> findDeletedUsers();

    /** 자동 로그인 토큰 저장 (토큰 + 생성 시각) */
    void saveTokenWithTimestamp(String email, String token);

    /** 자동 로그인 토큰으로 사용자 조회 */
    Optional<User> findByLoginToken(String token);

    /** 자동 로그인 토큰 삭제 */
    void clearLoginToken(String email);

    /** 사용자 정보 저장 (DB 저장용) */
    void save(User user);

    /** 아이디 찾기 (이름 + 전화번호) */
    Optional<User> findByNameAndPhone(String name, String phone);

    /** 비밀번호 찾기 (이메일 + 전화번호) */
    Optional<User> findByEmailAndPhone(String email, String phone);

    /** 비밀번호 변경 */
    void updatePassword(String email, String newPassword);

    /** 회원 조회 */
    Optional<User> findById(String email);

    /** 회원 정보 수정 */
    void updateUser(User user);

    /** 프로필 이미지 삭제 */
    void removeProfileImage(String email);
}

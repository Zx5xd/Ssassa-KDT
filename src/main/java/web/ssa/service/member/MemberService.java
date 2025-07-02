package web.ssa.service.member;

import web.ssa.dto.member.MemberDTO;
import web.ssa.entity.member.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    /** 관리자: 회원 삭제 (논리 삭제) */
    void deleteUserByAdmin(String email);

    /** 탈퇴된 회원 복구 */
    void restoreUser(String email);

    /** 전체 일반 회원 목록 (삭제 여부 무관, 관리자 제외) */
    List<User> findAllUsersExcludingAdminAndDeleted();

    /** 관리자: 정상 회원만 조회 (삭제되지 않음 + 관리자 제외) */
    List<User> findActiveUsers(); // deleted = "N" && role != "ADMIN"

    /** 관리자: 탈퇴 회원만 조회 (삭제됨 + 관리자 제외) */
    List<User> findDeletedUsers(); // deleted = "Y" && role != "ADMIN"

    /** 자동 로그인 토큰 저장 */
    void saveToken(String email, String token);

    /** 자동 로그인 토큰 저장 + 시간 */
    void saveTokenWithTimestamp(String email, String token, LocalDateTime createdAt);

    /** 자동 로그인 토큰 유효성 검사 */
    User validateAutoLoginToken(String token);

    /** 자동 로그인 토큰 삭제 */
    void clearLoginToken(String email);

    /** 아이디 찾기 (이름 + 전화번호) */
    Optional<User> findByNameAndPhone(String name, String phone);

    /** 비밀번호 찾기 (이메일 + 전화번호) */
    Optional<User> findByEmailAndPhone(String email, String phone);

    /** 비밀번호 변경 */
    void updatePassword(String email, String newPassword);

    /** 회원 조회 (이메일 기준) */
    Optional<User> findById(String email);

    /** 회원 정보 수정 */
    void updateUser(User user);

    /** 프로필 이미지 삭제 */
    void removeProfileImage(String email);
}

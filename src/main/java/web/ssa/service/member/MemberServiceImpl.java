package web.ssa.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.ssa.dto.member.MemberDTO;
import web.ssa.entity.member.User;
import web.ssa.repository.member.MemberRepository;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") +
            "/src/main/webapp/resources/uploads/";

    /** 회원가입 */
    @Override
    public void register(MemberDTO dto) {
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        if (memberRepository.existsByNickname(dto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setNickname(dto.getNickname());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setEmailVerified(dto.isEmailVerified());
        user.setEmailVerifiedAt(dto.isEmailVerified() ? LocalDateTime.now() : null);
        user.setDeleted("N");

        memberRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return memberRepository.existsByNickname(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    @Override
    public User login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> {
                    if ("Y".equalsIgnoreCase(user.getDeleted())) {
                        throw new IllegalStateException("이미 탈퇴한 계정입니다.");
                    }
                    return user;
                })
                .orElse(null);
    }

    @Override
    public void withdrawUser(String email) {
        User user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setDeleted("Y");
        user.clearLoginToken();
        memberRepository.save(user);
    }

    @Override
    public void deleteUserByAdmin(String email) {
        User user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setDeleted("Y");
        user.clearLoginToken();
        memberRepository.save(user);
    }

    @Override
    public void restoreUser(String email) {
        User user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setDeleted("N");
        memberRepository.save(user);
    }

    @Override
    public List<User> findActiveUsers() {
        return memberRepository.findByRoleNotAndDeleted("ADMIN", "N");
    }

    @Override
    public List<User> findDeletedUsers() {
        return memberRepository.findByRoleNotAndDeleted("ADMIN", "Y");
    }

    @Override
    public List<User> findAllUsersExcludingAdminAndDeleted() {
        return memberRepository.findByRoleNot("ADMIN");
    }

    @Override
    public void saveToken(String email, String token) {
        saveTokenWithTimestamp(email, token, LocalDateTime.now());
    }

    @Override
    public void saveTokenWithTimestamp(String email, String token, LocalDateTime createdAt) {
        User user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        user.setLoginToken(token);
        user.setLoginTokenCreatedAt(createdAt);
        memberRepository.save(user);
    }

    @Override
    public User validateAutoLoginToken(String token) {
        return memberRepository.findByLoginToken(token)
                .filter(User::isLoginTokenValid)
                .orElse(null);
    }

    @Override
    public void clearLoginToken(String email) {
        memberRepository.findByEmail(email).ifPresent(user -> {
            user.clearLoginToken();
            memberRepository.save(user);
        });
    }

    @Override
    public Optional<User> findByNameAndPhone(String name, String phone) {
        return memberRepository.findByNameAndPhone(name, phone);
    }

    @Override
    public Optional<User> findByEmailAndPhone(String email, String phone) {
        return memberRepository.findByEmailAndPhone(email, phone);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        User user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setPassword(newPassword);
        memberRepository.save(user);
    }

    @Override
    public Optional<User> findById(String email) {
        return memberRepository.findById(email);
    }

    @Override
    public void updateUser(User user) {
        User existing = memberRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        existing.setName(user.getName());
        existing.setNickname(user.getNickname());
        existing.setPhone(user.getPhone());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(user.getPassword());
        }

        // ✅ null이더라도 profileImage 업데이트
        existing.setProfileImage(user.getProfileImage());

        memberRepository.save(existing);
    }

    /** ✅ 프로필 이미지 삭제 */
    @Override
    public void removeProfileImage(String email) {
        User user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        if (user.getProfileImage() != null) {
            try {
                File file = new File(System.getProperty("user.dir") + "/src/main/webapp" + user.getProfileImage());
                Files.deleteIfExists(file.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }

            user.setProfileImage(null);
            memberRepository.save(user);
        }
    }
}

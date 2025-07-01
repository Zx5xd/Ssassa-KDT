package web.ssa.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final String UPLOAD_DIR = System.getProperty("user.dir") +
            "/src/main/webapp/resources/uploads/";

    @Override
    @Transactional
    public void register(MemberDTO dto) {
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }
        if (memberRepository.existsByNickname(dto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        User user = new User();
        user.setEmail(dto.getEmail());

        // ✅ 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

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
    @Transactional
    public void save(User user) {
        memberRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    @Override
    public User login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .filter(user -> !user.isDeleted())
                .orElse(null);
    }

    @Override
    @Transactional
    public void withdrawUser(String email) {
        User user = getUserByEmail(email);
        user.setDeleted("Y");
        user.clearLoginToken();
        memberRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserByAdmin(String email) {
        User user = getUserByEmail(email);
        user.setDeleted("Y");
        user.clearLoginToken();
        memberRepository.save(user);
    }

    @Override
    @Transactional
    public void restoreUser(String email) {
        User user = getUserByEmail(email);
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
    @Transactional
    public void saveTokenWithTimestamp(String email, String token) {
        User user = getUserByEmail(email);
        user.setLoginToken(token);
        user.setLoginTokenCreatedAt(LocalDateTime.now());
        memberRepository.save(user);
    }

    @Override
    public Optional<User> findByLoginToken(String token) {
        return memberRepository.findByLoginToken(token);
    }

    @Override
    @Transactional
    public void clearLoginToken(String email) {
        User user = getUserByEmail(email);
        user.clearLoginToken();
        memberRepository.save(user);
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
    @Transactional
    public void updatePassword(String email, String newPassword) {
        User user = getUserByEmail(email);
        // ✅ 비밀번호 암호화 후 저장
        user.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(user);
    }

    @Override
    public Optional<User> findById(String email) {
        return memberRepository.findById(email);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User existing = getUserByEmail(user.getEmail());
        existing.setName(user.getName());
        existing.setNickname(user.getNickname());
        existing.setPhone(user.getPhone());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            // ✅ 비밀번호 암호화 후 저장
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        existing.setProfileImage(user.getProfileImage());
        memberRepository.save(existing);
    }

    @Override
    @Transactional
    public void removeProfileImage(String email) {
        User user = getUserByEmail(email);
        if (user.getProfileImage() != null) {
            try {
                File file = new File(UPLOAD_DIR + user.getProfileImage());
                Files.deleteIfExists(file.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setProfileImage(null);
            memberRepository.save(user);
        }
    }

    private User getUserByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + email));
    }
}

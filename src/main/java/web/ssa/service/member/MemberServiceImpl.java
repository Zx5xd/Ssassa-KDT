package web.ssa.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.ssa.dto.member.MemberDTO;
import web.ssa.entity.member.User;
import web.ssa.repository.member.MemberRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 🔐 회원가입 처리
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
        user.setPassword(dto.getPassword()); // ⚠ 실제 서비스에서는 반드시 비밀번호 암호화 필요
        user.setName(dto.getName());
        user.setNickname(dto.getNickname());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setEmailVerified(dto.isEmailVerified());
        user.setEmailVerifiedAt(dto.isEmailVerified() ? LocalDateTime.now() : null);
        user.setDeleted("N");

        memberRepository.save(user);
    }

    // 🔍 닉네임 중복 확인
    @Override
    public boolean existsByUsername(String username) {
        return memberRepository.existsByNickname(username);
    }

    // 🔍 이메일 중복 확인
    @Override
    public boolean existsByEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    // 🔑 로그인 처리
    @Override
    public User login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password)) // ⚠ 평문 비교 (보안 적용 필요)
                .map(user -> {
                    if ("Y".equalsIgnoreCase(user.getDeleted())) {
                        throw new IllegalStateException("이미 탈퇴한 계정입니다.");
                    }
                    return user;
                })
                .orElse(null);
    }

    // ❌ 회원 탈퇴 처리
    @Override
    public void deleteUser(String email) {
        User user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setDeleted("Y");
        user.setLoginToken(null);
        user.setLoginTokenCreatedAt(null);
        memberRepository.save(user);
    }

    // ✅ 자동 로그인 토큰 저장 (현재 시간 기준)
    @Override
    public void saveToken(String email, String token) {
        saveTokenWithTimestamp(email, token, LocalDateTime.now());
    }

    // ✅ 자동 로그인 토큰 저장 (시간 지정)
    @Override
    public void saveTokenWithTimestamp(String email, String token, LocalDateTime createdAt) {
        User user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        user.setLoginToken(token);
        user.setLoginTokenCreatedAt(createdAt);
        memberRepository.save(user);
    }

    // ✅ 자동 로그인 토큰 유효성 검사
    @Override
    public User validateAutoLoginToken(String token) {
        return memberRepository.findByLoginToken(token)
                .filter(user -> {
                    LocalDateTime issuedAt = user.getLoginTokenCreatedAt();
                    return issuedAt != null && issuedAt.plusMinutes(5).isAfter(LocalDateTime.now());
                })
                .orElse(null);
    }

    // ✅ 자동 로그인 토큰 삭제
    @Override
    public void clearLoginToken(String email) {
        memberRepository.findByEmail(email).ifPresent(user -> {
            user.clearLoginToken();
            memberRepository.save(user);
        });
    }

    // ✅ 이름 + 전화번호로 사용자 조회 (아이디 찾기)
    @Override
    public Optional<User> findByNameAndPhone(String name, String phone) {
        return memberRepository.findByNameAndPhone(name, phone);
    }

    // ✅ 이메일 + 전화번호로 사용자 조회 (비밀번호 재설정)
    @Override
    public Optional<User> findByEmailAndPhone(String email, String phone) {
        return memberRepository.findByEmailAndPhone(email, phone);
    }

    // ✅ 비밀번호 변경
    @Override
    public void updatePassword(String email, String newPassword) {
        User user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.setPassword(newPassword); // ⚠ 실제 서비스는 반드시 암호화 필요
        memberRepository.save(user);
    }
}

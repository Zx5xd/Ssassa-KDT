package web.ssa.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import web.ssa.entity.member.User;
import web.ssa.repository.member.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenCleanupService {

    private final MemberRepository memberRepository;

    /**
     * 1분마다 실행
     * - DB에서 자동 로그인 토큰이 존재하는 사용자 조회
     * - 유효하지 않은 토큰은 초기화
     */
    @Scheduled(fixedRate = 60000) // 60,000ms = 1분
    public void removeExpiredTokens() {
        List<User> users = memberRepository.findByLoginTokenIsNotNull();

        for (User user : users) {
            if (!user.isLoginTokenValid()) {
                user.clearLoginToken();
                memberRepository.save(user);
            }
        }
    }
}

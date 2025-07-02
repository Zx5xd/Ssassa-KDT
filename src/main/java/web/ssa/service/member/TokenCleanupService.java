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

    // 1분마다 실행 (60000ms)
    @Scheduled(fixedRate = 60000)
    public void removeExpiredTokens() {
        List<User> users = memberRepository.findAll();

        for (User user : users) {
            if (!user.isLoginTokenValid()) {
                user.clearLoginToken();
                memberRepository.save(user);
            }
        }
    }
}
package web.ssa.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import web.ssa.entity.member.User;
import java.io.IOException;
import java.util.Optional;

@Component
public class AutoLoginFilter implements Filter {

    private final web.ssa.repository.member.MemberRepository memberRepository;

    public AutoLoginFilter(web.ssa.repository.member.MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        // 로그인 안 되어 있으면 쿠키 검사
        if (session == null || session.getAttribute("loginUser") == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("loginToken".equals(cookie.getName())) {
                        String token = cookie.getValue();
                        Optional<User> userOpt = memberRepository.findByLoginToken(token); // ✅ 수정된 부분
                        if (userOpt.isPresent()) {
                            HttpSession newSession = request.getSession();
                            newSession.setAttribute("loginUser", userOpt.get());
                        }
                        break;
                    }
                }
            }
        }

        // 다음 필터 또는 컨트롤러로 요청 전달
        chain.doFilter(req, res);
    }
}
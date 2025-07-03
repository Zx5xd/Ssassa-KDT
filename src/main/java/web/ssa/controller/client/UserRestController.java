package web.ssa.controller.client;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.ssa.dto.member.SimpleMemberDTO;
import web.ssa.entity.member.User;

@RestController
@RequestMapping("api/*")
public class UserRestController {
    @GetMapping("/me")
    public ResponseEntity<?> getSessionUser(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        SimpleMemberDTO dto = new SimpleMemberDTO(user.getEmail(), user.getNickname());
        return ResponseEntity.ok(dto);
    }
}

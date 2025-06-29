package web.ssa.dto.member;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDTO {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private String role = "USER";
    private String profileImage;
    private boolean emailVerified = false;
    private LocalDateTime emailVerifiedAt;
}

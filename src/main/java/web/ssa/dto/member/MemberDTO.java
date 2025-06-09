package web.ssa.dto.member;

import lombok.Data;

@Data
public class MemberDTO {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private String role;

}

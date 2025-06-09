package web.ssa.service.member;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.ssa.dto.member.MemberDTO;
import web.ssa.entity.member.User;
import web.ssa.repository.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public void register(MemberDTO dto) {
        if(memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");

        }
        if (memberRepository.existsByNickname(dto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName()); //
        user.setNickname(dto.getNickname());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setEmailVerified(false);
        user.setEmailVerifiedAt(null);
        user.setDeleted("N");

        memberRepository.save(user);
    }

}

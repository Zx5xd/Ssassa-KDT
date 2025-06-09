package web.ssa.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import web.ssa.entity.member.User;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByEmailAndEmailVerifiedTrue(String email);
}
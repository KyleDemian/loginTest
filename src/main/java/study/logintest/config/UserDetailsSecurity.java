package study.logintest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import study.logintest.user.entity.Member;
import study.logintest.user.entity.Role;
import study.logintest.user.repository.MemberRepository;
import study.logintest.user.service.MemberService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDetailsSecurity implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> findOne = memberRepository.findByLoginId(loginId);
        Member member = findOne.orElseThrow(() -> new UsernameNotFoundException("없음."));

        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .roles(String.valueOf(Role.USER))
                .build()
                ;
    }
}

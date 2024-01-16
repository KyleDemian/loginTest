package study.logintest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.logintest.user.entity.Member;
import study.logintest.user.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // ref. https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> findUser = memberRepository.findByLoginId(username);
        Member member = findUser.orElseThrow(() -> new UsernameNotFoundException("ID 없음"));

        return (UserDetails) Member.builder()
                .loginId(member.getLoginId())
                .password(member.getPassword())
                .role(member.getRole())
                .build();
    }
}

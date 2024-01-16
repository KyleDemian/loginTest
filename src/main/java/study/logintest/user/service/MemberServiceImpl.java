package study.logintest.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.logintest.user.dto.MemberDto;
import study.logintest.user.entity.Member;
import study.logintest.user.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long signUp(MemberDto memberDto) throws Exception{
        if (memberRepository.findByLoginId(memberDto.getLoginId()).isPresent()) {
            throw new IllegalArgumentException("이미 동일 아이디 있음");
        }

        return memberRepository.save(Member.builder()
                .loginId(memberDto.getLoginId())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .build()).getId()
        ;
    }

    @Override
    public Member findByMember(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException(loginId));
    }
}

package study.logintest.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import study.logintest.user.dto.MemberDto;
import study.logintest.user.entity.Member;

public interface MemberService {
    Long signUp(MemberDto memberDto) throws Exception;

    Member findByMember(String loginId);

}

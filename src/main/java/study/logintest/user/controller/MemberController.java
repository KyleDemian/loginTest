package study.logintest.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import study.logintest.user.dto.MemberDto;
import study.logintest.user.service.MemberService;

//@RestController
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService userService;

    @GetMapping("/")
    public String main() {
        return "/index";
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody MemberDto memberDto) throws Exception {
        Long result = userService.signUp(memberDto);
        return "회원가입 성공";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}

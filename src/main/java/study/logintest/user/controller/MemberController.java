package study.logintest.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import study.logintest.user.dto.MemberDto;
import study.logintest.user.service.MemberService;

import java.security.Principal;

//@RestController
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService userService;

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "login/login";
    }

    @PostMapping("/login-process")
    public String login(@ModelAttribute MemberDto memberDto) {
        userService.loadUserByUsername(memberDto.getLoginId());
        return "redirect:/";
    }


    @GetMapping("/sign-up")
    public String signUpForm(Model model) throws Exception {
        model.addAttribute("memberDto", new MemberDto());
        return "login/signup";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute MemberDto memberDto) throws Exception {
       userService.signUp(memberDto);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }
}

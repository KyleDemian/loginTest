package study.logintest.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import study.logintest.user.dto.UserDto;
import study.logintest.user.service.UsersService;

//@RestController
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UsersService userService;

    @GetMapping("/")
    public String main() {
        return "/index";
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserDto userDto) throws Exception {
        userService.signUp(userDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest(){
        return "jwtTest 요청 성공";
    }
}

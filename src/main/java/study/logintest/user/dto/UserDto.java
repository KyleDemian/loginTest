package study.logintest.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {

    private String email;
    private String password;
    private String nickname;
    private int age;
}

package study.logintest.user.service;

import study.logintest.user.dto.UserDto;

public interface UserService {
    void signUp(UserDto userDto) throws Exception;
}

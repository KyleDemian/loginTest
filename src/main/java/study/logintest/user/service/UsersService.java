package study.logintest.user.service;

import study.logintest.user.dto.UserDto;

public interface UsersService {
    void signUp(UserDto userDto) throws Exception;
}

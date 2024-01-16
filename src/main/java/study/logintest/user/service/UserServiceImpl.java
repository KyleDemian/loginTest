package study.logintest.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.logintest.user.dto.UserDto;
import study.logintest.user.entity.Role;
import study.logintest.user.entity.Users;
import study.logintest.user.repository.UsersRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UsersService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserDto userDto) throws Exception{
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("메일 이미 있음");
        }

        if (userRepository.findByNickname(userDto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 이름 있음");
        }

        Users user = Users.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .nickname(userDto.getNickname())
                .age(userDto.getAge())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }
}

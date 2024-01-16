package study.logintest.user.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.logintest.user.entity.Users;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
//
    Optional<Users> findByNickname(String nickname);

    Optional<Users> findByRefreshToken(String refreshToken);
}

package study.logintest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.CredentialExpiredException;

@Component
@RequiredArgsConstructor
public class UserDetailAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsSecurity userDetailsSecurity;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDetails users = userDetailsSecurity.loadUserByUsername(loginId);

        try {
            if (!passwordEncoder.matches(password, users.getPassword())) {
                throw new BadCredentialsException("Password is invalid");
            }
            if (!users.isAccountNonExpired()) {
                //계정 만료 여부
                throw new CredentialExpiredException("Account is expired");
            } else if (!users.isAccountNonLocked()) {
                //계정 잠금 여부
                throw new AccountLockedException("Account is locked");
            } else if (!users.isEnabled()) {
                //계정 사용 여부
                throw new LockedException("Can't use account");
            } else if (!users.isCredentialsNonExpired()) {
                //계정 비밀번호 만료 여부
                throw new CredentialExpiredException("Credentials is expired");
            }
        } catch (CredentialExpiredException e) {
            e.printStackTrace();
        } catch (AccountLockedException e) {
            e.printStackTrace();
        }

        return new UsernamePasswordAuthenticationToken(users, null, users.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

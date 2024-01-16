package study.logintest.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String loginId;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String loginId, String password, String auth) {
        this.loginId = loginId;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override   //계정이 만료되었는지 확인
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override   //계정이 잠금되었는지 확인
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override   //비밀번호가 만료되었는지 확인
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override   //계정이 사용 가능한지 확인
    public boolean isEnabled() {
        return true;
    }
}

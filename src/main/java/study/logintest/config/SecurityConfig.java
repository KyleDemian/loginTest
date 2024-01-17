package study.logintest.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import study.logintest.user.entity.Role;
import study.logintest.user.service.MemberServiceImpl;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberServiceImpl memberService;
    private final DataSource dataSource;
    
    //ref.
    //https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
    //https://velog.io/@letsdev/Spring-Boot-3.1Spring-6.1-Security-Config-csrf-is-deprecated-and-marked-for-removal
    //https://dmaolon00.tistory.com/entry/authorizeRequests-is-deprecated-%ED%95%B4%EA%B2%B0-Spring-Security-Configuration
    //https://github.com/shinsunyoung/springboot-developer/issues/5

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> request
                    .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/")).authenticated()    // index 페이지만 로그인 요청
                    .anyRequest()
                    .permitAll()
            )
            .formLogin(login -> login  // form 방식 로그인 사용
                    .loginPage("/login")
                    .usernameParameter("loginId")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login-process")
                    .defaultSuccessUrl("/", true)
                    .permitAll()
            )
            .logout(withDefaults())
//            .logout(logout -> logout
//                .logoutSuccessUrl("/"))
            .rememberMe(rememberMe -> rememberMe        // Front name = remember-me 사용시 호출.
                    .userDetailsService(memberService)
                    .tokenRepository(tokenRepository()))
            ;

        return http.build();
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web
                .ignoring()
                .requestMatchers("/static/**");
    }


}

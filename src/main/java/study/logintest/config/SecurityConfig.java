package study.logintest.config;

import jakarta.servlet.DispatcherType;
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
import study.logintest.user.entity.Role;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //ref.
    //https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
    //https://velog.io/@letsdev/Spring-Boot-3.1Spring-6.1-Security-Config-csrf-is-deprecated-and-marked-for-removal
    //https://dmaolon00.tistory.com/entry/authorizeRequests-is-deprecated-%ED%95%B4%EA%B2%B0-Spring-Security-Configuration
    //https://github.com/shinsunyoung/springboot-developer/issues/5

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors( cors -> cors.disable())
//            .cors((httpSecurityCorsConfigurer ->
//                    httpSecurityCorsConfigurer.disable()))
            .authorizeHttpRequests(request -> request
                    .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                    .anyRequest().authenticated()  // 어떠한 요청이라도 인증필요
            )
            .formLogin(login -> login  // form 방식 로그인 사용
                    .defaultSuccessUrl("/swagger-ui/index.html", true)
                    .permitAll()
            )
            .logout(withDefaults());   // 로그아웃은 기본설정으로 (/logout으로 인증해제)

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserDetailsService userDetailService) throws Exception{
    return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailService)
            .passwordEncoder(bCryptPasswordEncoder)
            .build()
            ;
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

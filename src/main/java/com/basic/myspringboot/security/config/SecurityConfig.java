package com.basic.myspringboot.security.config;

import com.basic.myspringboot.security.service.UserInfoUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    // /api/users/welcome 요청은 인증이 없어도 접근가능
                    auth.requestMatchers("/users/welcome","/userinfos/new").permitAll()
                            // /api/users/**   요청은 인증이 필요함
                            .requestMatchers("/api/users/**").authenticated();
                })
                .formLogin(withDefaults())
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("adminboot")
                .password(encoder.encode("pwd1"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("userboot")
                .password(encoder.encode("pwd2"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }*/

    // 커스텀한 UserDetailService를 스프링 빈을 설정
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }

    //DaoAuthenticationProvider 스프링빈으로 설정
    @Bean
    public AuthenticationProvider authenticationProvider(){
        //DaoAuthenticationProvider에게 UserDetailService와 passwordEncoder를 설정
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
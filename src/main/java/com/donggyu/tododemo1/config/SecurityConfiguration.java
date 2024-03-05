package com.donggyu.tododemo1.config;

import com.donggyu.tododemo1.util.Loginfilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((auth) -> auth.disable()); // JWT를 이용하기 때문에 CSRF 방어 불필요
        httpSecurity.formLogin((auth) -> auth.disable()); // Form 로그인 불필요
        httpSecurity.httpBasic((auth) -> auth.disable()); // HTTP Basic 인증 불필요
        // API 경로 별 인가 구성
        httpSecurity.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/api/v1/users/join", "/api/v1/users/login", "/login").permitAll()
                .requestMatchers("/api/v1/users/admin").hasRole("ADMIN")
                .anyRequest().authenticated());
        httpSecurity.addFilterAt(new Loginfilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);
        // 세션 구성
        httpSecurity.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {return configuration.getAuthenticationManager();}
}

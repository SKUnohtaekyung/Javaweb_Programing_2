package com.example.jar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정 클래스입니다.
 * 보안 필터 체인, 암호화 인코더 등을 설정합니다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 비밀번호 암호화를 위한 BCrypt 엔코더 빈을 등록합니다.
     * 
     * @return BCryptPasswordEncoder 객체
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * HTTP 보안 설정을 구성합니다.
     * CSRF 비활성화, 접근 권한 설정, 로그인/로그아웃 등을 처리합니다.
     * 
     * @param http HttpSecurity 객체
     * @return 구성된 SecurityFilterChain
     * @throws Exception 설정 중 오류 발생 시
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화 (개발 편의성 및 REST API 등을 위해)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 특정 경로들에 대한 모든 접근 허용
                        .requestMatchers(
                                "/",
                                "/about_detailed",
                                "/board_list",
                                "/board_view/**",
                                "/board_edit/**",
                                "/board/**",
                                "/article_list",
                                "/article_list/**",
                                "/api/boards/**",
                                "/join_new",
                                "/api/members",
                                "/member_login",
                                "/api/login_check",
                                "/api/contacts",
                                "/hello1004",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/img/**"
                        ).permitAll()
                        // 그 외 모든 요청도 허용 (실제 운영 시에는 보안 강화 필요)
                        .anyRequest().permitAll()
                )
                // 세션 만료 시 로그인 페이지로 이동 설정 삭제 (기본값 사용)
                // .sessionManagement(session -> session.invalidSessionUrl("/member_login"))
                // 권한 없음 예외 발생 시 403 에러 페이지로 이동
                .exceptionHandling(ex -> ex.accessDeniedPage("/error/403"))
                // Form 로그인 비활성화 (커스텀 로그인 사용)
                .formLogin(form -> form.disable())
                // 로그아웃 설정
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/"));

        return http.build();
    }
}

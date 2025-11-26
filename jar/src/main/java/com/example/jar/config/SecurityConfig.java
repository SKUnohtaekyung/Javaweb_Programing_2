package com.example.jar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/about_detailed",
                                "/board_list",
                                "/board_view/**",
                                "/board_edit/**",
                                "/board/**",
                                "/article_list",
                                "/article_list/**",
                                "/api/board/**",
                                "/api/boards/**",
                                "/api/article/**",
                                "/join_new",
                                "/api/members",
                                "/member_login",
                                "/api/login_check",
                                "/hello1004",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/img/**"
                        ).permitAll()
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.invalidSessionUrl("/member_login"))
                .exceptionHandling(ex -> ex.accessDeniedPage("/error/403"))
                .formLogin(form -> form.disable())
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/"));

        return http.build();
    }
}



package com.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors().and() // Enable CORS
            .csrf().disable() // Disable CSRF for simplicity, consider enabling it in production with appropriate configuration
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/user/register", "/password-strength/check","/user/login","/user/check-password-strength").permitAll() // Allow access to the registration and password strength endpoints
                .anyRequest().authenticated()
            )
            .httpBasic(); // Or formLogin() if using form-based authentication

        return http.build();
    }
}

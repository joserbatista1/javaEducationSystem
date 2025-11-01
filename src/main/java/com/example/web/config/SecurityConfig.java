package com.example.web.config;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Authorization: Define which URLs are protected
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll() // Allow static and login page
                        .anyRequest().authenticated() // All other URLs require authentication
                )
                // 2. Form-Based Login (Session Management)
                .formLogin(form -> form
                        .loginPage("/login")             // The URL to display the login form
                        .loginProcessingUrl("/login")    // The URL where Spring Security intercepts the POST
                        .defaultSuccessUrl("/main", true)    // Redirect after success
                        .failureUrl("/login?error")      // Redirect after failure
                        .permitAll()
                )
                // 3. Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login?logout") // Redirect after logout
                        .permitAll()
                )
                // 4. CSRF protection (Recommended for web apps. Spring handles the token generation)
                .csrf(csrf -> csrf.disable()); // TEMPORARILY disable for simplicity if you don't use thymeleaf security tags

        return http.build();
    }

    // Define the Password Encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
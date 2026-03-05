package com.wipro.cozyhaven.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean    
    public PasswordEncoder passwordEncoder() {          
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userDetailsService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }
    
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .authenticationProvider(authProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/users/register",
                    "/api/users/login",
                    "/api/users/getRoleByEmail",
                    "/api/hotels/getall",
                    "/api/rooms/getall",
                    "/api/hotels/search/**",
                    "/index.html",
                    "/hotels.html",
                    "/rooms.html",
                    "/register.html",
                    "/login.html",
                    "/user-dashboard.html",
                    "/owner-dashboard.html",
                    "/admin-dashboard.html",
                    "/user-profile.html",
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
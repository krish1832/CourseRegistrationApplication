package com.example.Course.Registration.App.Config;

import com.example.Course.Registration.App.Util.JwtAuthenticationEntryPoint;
import com.example.Course.Registration.App.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

      @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

      @Autowired
      private JwtAuthenticationEntryPoint point;

      @Autowired
    private final JwtUtil jwtUtil;


    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, JwtUtil jwtUtil) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/user-login/**").permitAll() // Allow login endpoint without authentication
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Allow Swagger UI
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // Admin access
                        .requestMatchers("/student/**").hasAuthority("ROLE_STUDENT") // Student access
                        .requestMatchers("/professor/**").hasAuthority("ROLE_PROFESSOR") // Professor access
                        .anyRequest().authenticated() // Protect all other endpoints
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point)) // Custom Authentication Entry Point
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
                .csrf(csrf -> csrf.disable()); // Disable CSRF
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

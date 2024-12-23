package com.hackathon.finservice.Config;

import com.hackathon.finservice.Util.JWTTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration for the application.
 * <p>
 * This class configures the application's security using Spring Security.
 * It defines authentication, password encoding, and JWT filter configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig{
    private final JWTTokenFilter jwtTokenFilter;

    /**
     * Constructor for the SecurityConfig class.
     *
     * @param jwtTokenFilter The JWT token filter used to validate JWT tokens in each request.
     */
    public SecurityConfig(JWTTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    /**
     * Bean that configures the AuthenticationManager, responsible for handling authentication in the application.
     *
     * @param authConfig The authentication configuration provided by Spring.
     * @return A configured AuthenticationManager.
     * @throws Exception If there is an error in the AuthenticationManager configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Bean that configures the password encoder.
     * Uses BCrypt to encode passwords before storing them in the database.
     *
     * @return A BCrypt-based PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean that configures the HTTP security of the application.
     * Disables CSRF, allows public access to certain routes, and applies a JWT security filter.
     *
     * @param http The HttpSecurity object that allows customizing the security configuration.
     * @return A configured SecurityFilterChain.
     * @throws Exception If there is an error in the HTTP security configuration.
     */
    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/health").permitAll()
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Access Denied");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Access Denied");
                        })
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}


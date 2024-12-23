package com.hackathon.finservice.Util;

import com.hackathon.finservice.Exception.InvalidTokenException;
import com.hackathon.finservice.Services.JWTBlacklistService;
import com.hackathon.finservice.Services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@WebFilter("/*")
public class JWTTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String jwtHeader;

    @Value("${jwt.prefix}")
    private String jwtPrefix;

    private final JWTBlacklistService jwtBlacklistService;
    private final JwtUtil jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    public JWTTokenFilter(JWTBlacklistService jwtBlacklistService, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtBlacklistService =jwtBlacklistService;
        this.jwtUtils =jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(getJwtHeader());

        if (authorizationHeader != null && authorizationHeader.startsWith(getJwtPrefix())) {
            String token = authorizationHeader.substring(7);

            if (jwtBlacklistService.isTokenInvalid(token)) {
                throw new InvalidTokenException("Access Denied");
            }
            processTokenAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }

    private void processTokenAuthentication(String token) {
        try {
            if (jwtUtils.validateToken(token)) {
                String username = jwtUtils.extractUsername(token);
                var userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            throw new InvalidTokenException("Access Denied");
        }
    }

    private String getJwtHeader() {
        return jwtHeader;
    }

    private String getJwtPrefix() {
        return jwtPrefix;
    }
}


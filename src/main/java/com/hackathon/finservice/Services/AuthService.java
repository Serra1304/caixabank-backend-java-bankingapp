package com.hackathon.finservice.Services;

import com.hackathon.finservice.Entities.Token;
import com.hackathon.finservice.Exception.InvalidCredentialsException;
import com.hackathon.finservice.Exception.UserNotFoundException;
import com.hackathon.finservice.Repositories.TokenRepository;
import com.hackathon.finservice.Repositories.UserRepository;
import com.hackathon.finservice.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * AuthService is a service responsible for handling authentication operations,
 * such as user login and logout. It includes the generation and management of
 * JWT tokens and their storage in the database.
 *
 * <p>This service interacts with other system components, such as the user repository,
 * token repository, user service, and utilities for generating and validating JWT tokens.</p>
 */
@Service
public class AuthService {
    @Value("${jwt.expiration}")
    private int jwtExpiration;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;
    private final JWTBlacklistService jwtBlacklistService;

    /**
     * Constructor for AuthService.
     *
     * @param authenticationManager manages user authentication.
     * @param userDetailsService loads user details.
     * @param userRepository provides access to user data.
     * @param tokenRepository manages token persistence.
     * @param jwtUtil provides utilities for generating and validating JWT tokens.
     * @param jwtBlacklistService handles the blacklist of JWT tokens.
     */
    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       UserDetailsServiceImpl userDetailsService,
                       UserRepository userRepository,
                       TokenRepository tokenRepository,
                       JwtUtil jwtUtil,
                       JWTBlacklistService jwtBlacklistService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.jwtUtil = jwtUtil;
        this.jwtBlacklistService = jwtBlacklistService;
    }

    /**
     * Logs in a user by authenticating their credentials and generating a JWT token.
     *
     * @param email the user's email address.
     * @param password the user's password.
     * @return a JWT token for the authenticated user.
     * @throws UserNotFoundException if the user does not exist.
     * @throws InvalidCredentialsException if the credentials are invalid.
     */
    public String login(String email, String password) {
        validateUserExists(email);
        authenticateUser(email, password);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtUtil.generateToken(userDetails);

        saveToken(email, token);

        return token;
    }

    /**
     * Invalidates a JWT token by adding it to the blacklist.
     *
     * @param token the JWT token to invalidate.
     * @throws IllegalArgumentException if the token is not found in the database.
     */
    public void logout(String token) {
        jwtBlacklistService.invalidateToken(token);
    }

    /**
     * Checks if a user with the given email exists in the database.
     *
     * @param email the user's email address.
     * @throws UserNotFoundException if the user does not exist.
     */
    private void validateUserExists(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new UserNotFoundException("User not found for the given identifier: " + email);
        }
    }

    /**
     * Authenticates a user using their email and password.
     *
     * @param email the user's email address.
     * @param password the user's password.
     * @throws InvalidCredentialsException if the credentials are invalid.
     */
    private void authenticateUser(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Bad credentials");
        }
    }

    /**
     * Saves a JWT token to the database, associating it with the corresponding user.
     *
     * @param email the user's email address.
     * @param token the JWT token generated for the user.
     * @throws IllegalArgumentException if the user is not found in the database.
     */
    private void saveToken(String email, String token) {
        Token newToken = new Token();
        newToken.setToken(token);
        newToken.setRevoked(false);
        newToken.setExpired(false);
        newToken.setCreatedAt(LocalDateTime.now());
        newToken.setExpiresAt(LocalDateTime.now().plusMinutes(getJwtExpiration()));
        newToken.setUser(userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("User not found")));

        tokenRepository.save(newToken);
    }

    /**
     * Retrieves the expiration time for JWT tokens configured in the application.
     *
     * @return the expiration time in minutes.
     */
    private int getJwtExpiration() {
        return jwtExpiration;
    }
}

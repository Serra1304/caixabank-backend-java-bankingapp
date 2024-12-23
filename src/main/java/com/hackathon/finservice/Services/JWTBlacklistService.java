package com.hackathon.finservice.Services;

import com.hackathon.finservice.Entities.Token;
import com.hackathon.finservice.Repositories.TokenRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for managing the invalidation and validation of JWT tokens.
 * Implements a blacklist mechanism by persisting and marking tokens as revoked in the database.
 * <p>
 * This service ensures that invalidated tokens cannot be used for further authentication or authorization.
 * </p>
 */
@Service
public class JWTBlacklistService {
    private final TokenRepository tokenRepository;

    /**
     * Constructs a new instance of {@code JWTBlacklistService}.
     *
     * @param tokenRepository the repository for interacting with the token database.
     */
    public JWTBlacklistService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * Invalidates a JWT token by marking it as revoked in the database.
     * <p>
     * If the token is not found or is already revoked, specific exceptions are thrown.
     * </p>
     *
     * @param token the JWT token to invalidate.
     * @throws IllegalArgumentException if the token is not found in the database.
     * @throws IllegalStateException if the token is already revoked.
     * @throws NullPointerException if the token is {@code null} or empty.
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * jwtBlacklistService.invalidateToken("example.jwt.token");
     * </pre>
     */
    public void invalidateToken(String token) {
        Token tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token not found in the database"));

        if (tokenEntity.isRevoked()) {
            throw new IllegalStateException("Token is already revoked");
        }

        tokenEntity.setRevoked(true);
        tokenRepository.save(tokenEntity);
    }
    /**
     * Checks if a given JWT token is invalid (revoked).
     *
     * @param token the JWT token to check.
     * @return {@code true} if the token is revoked, {@code false} otherwise.
     * @throws NullPointerException if the token is {@code null} or empty.
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * boolean isRevoked = jwtBlacklistService.isTokenInvalid("example.jwt.token");
     * </pre>
     */
    public boolean isTokenInvalid(String token) {
        return tokenRepository.findByToken(token)
                .map(Token::isRevoked)
                .orElse(false);
    }
}

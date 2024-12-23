package com.hackathon.finservice.Exception;

/**
 * Custom exception to indicate invalid token scenarios.
 * <p>
 * This exception is typically thrown when a JWT token (or any type of token used for authentication) is invalid, expired,
 * or does not meet the security criteria required for access to protected resources.
 * </p>
 *
 * <p>
 * It extends {@link RuntimeException}, enabling it to be used in runtime error handling and allowing centralized
 * management through mechanisms like Spring's {@link org.springframework.web.bind.annotation.ControllerAdvice}.
 * </p>
 *
 * <p>
 * Example scenarios where this exception might be thrown:
 * <ul>
 *     <li>The token has expired and is no longer valid.</li>
 *     <li>The token signature does not match or is corrupted.</li>
 *     <li>The token is not authorized to access certain resources.</li>
 * </ul>
 * </p>
 */
public class InvalidTokenException extends RuntimeException{

    /**
     * Constructs a new {@code InvalidTokenException} with the specified detail message.
     *
     * @param message the detail message providing context about the invalid token.
     */
    public InvalidTokenException(String message) {
        super(message);
    }
}

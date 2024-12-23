package com.hackathon.finservice.Exception;

/**
 * Custom exception to indicate invalid credentials during authentication.
 * <p>
 * This exception is thrown when a user provides incorrect credentials, such as:
 * <ul>
 *     <li>An invalid username or email address.</li>
 *     <li>An incorrect password.</li>
 * </ul>
 * </p>
 *
 * <p>
 * It extends {@link RuntimeException}, allowing it to be thrown without explicit try-catch blocks
 * and enabling centralized exception handling through mechanisms like Spring's
 * {@link org.springframework.web.bind.annotation.ControllerAdvice}.
 * </p>
 *
 * <p>
 * Example scenarios where this exception might be thrown:
 * <ul>
 *     <li>A login attempt fails due to mismatched credentials.</li>
 *     <li>An API client provides an invalid token or secret during authentication.</li>
 * </ul>
 * </p>
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidCredentialsException} with the specified detail message.
     *
     * @param message the detail message providing context about the invalid credentials.
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}

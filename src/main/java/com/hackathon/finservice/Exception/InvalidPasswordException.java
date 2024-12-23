package com.hackathon.finservice.Exception;

/**
 * Custom exception to indicate invalid password scenarios.
 * <p>
 * This exception is typically thrown when a password provided by a user does not meet the required criteria
 * or is incorrect during authentication or password reset processes.
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
 *     <li>Authentication fails due to an incorrect password.</li>
 *     <li>Password does not meet the application's security policies (e.g., length, complexity).</li>
 * </ul>
 * </p>
 */
public class InvalidPasswordException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidPasswordException} with the specified detail message.
     *
     * @param message the detail message providing context about the invalid password.
     */
    public InvalidPasswordException(String message) {
        super(message);
    }
}

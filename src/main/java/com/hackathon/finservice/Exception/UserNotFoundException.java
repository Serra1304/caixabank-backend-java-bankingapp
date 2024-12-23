package com.hackathon.finservice.Exception;

/**
 * Custom exception to indicate that a user was not found in the system.
 * <p>
 * This exception is typically thrown when a user with the given identifier (e.g., email or username) does not exist in the system.
 * It is commonly used in user management, authentication, or authorization processes.
 * </p>
 *
 * <p>
 * The exception extends {@link RuntimeException}, which allows it to be thrown during the normal flow of the application without
 * the need for explicit try-catch blocks.
 * </p>
 *
 * <p>
 * Example scenarios where this exception might be thrown:
 * <ul>
 *     <li>The system is unable to find a user with the provided email or username.</li>
 *     <li>An operation that requires a specific user fails because the user does not exist.</li>
 * </ul>
 * </p>
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code UserNotFoundException} with the specified detail message.
     *
     * @param message the detail message providing context about the missing user.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}

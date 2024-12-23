package com.hackathon.finservice.Exception;

/**
 * Custom exception to indicate that a requested account could not be found.
 * <p>
 * This exception is thrown when an account lookup operation fails due to the
 * specified account being unavailable or non-existent.
 * </p>
 *
 * <p>
 * It extends the {@link RuntimeException}, allowing it to be used without the
 * need for explicit try-catch blocks in every method. Instead, it can be
 * handled globally.
 * </p>
 *
 * <p>
 * Example scenarios where this exception might be thrown:
 * <ul>
 *     <li>The account index provided by the user does not correspond to an existing account.</li>
 *     <li>The account belongs to another user and is not accessible.</li>
 * </ul>
 * </p>
 *
 * <p>
 * This exception should include a clear and informative message to help
 * developers and clients understand the cause of the failure.
 * </p>
 */
public class AccountNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code AccountNotFoundException} with the specified detail message.
     *
     * @param message the detail message that provides additional context about the error.
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
}

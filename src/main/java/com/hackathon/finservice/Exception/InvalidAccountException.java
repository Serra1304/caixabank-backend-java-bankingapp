package com.hackathon.finservice.Exception;

/**
 * Custom exception to indicate that an operation has been attempted on an invalid account.
 * <p>
 * This exception is thrown when an account is found to be invalid due to various reasons such as:
 * <ul>
 *     <li>The account does not exist in the system.</li>
 *     <li>The account has been deactivated or closed.</li>
 *     <li>The account type is not allowed for a particular operation.</li>
 * </ul>
 * </p>
 *
 * <p>
 * It extends {@link RuntimeException}, allowing it to be used without explicit try-catch blocks
 * and enabling centralized exception handling mechanisms like Spring's
 * {@link org.springframework.web.bind.annotation.ControllerAdvice}.
 * </p>
 *
 * <p>
 * Example scenarios where this exception might be thrown:
 * <ul>
 *     <li>A transfer or withdrawal is attempted on an invalid account.</li>
 *     <li>Access to an account is denied due to incorrect or insufficient details.</li>
 * </ul>
 * </p>
 */
public class InvalidAccountException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidAccountException} with the specified detail message.
     *
     * @param message the detail message providing context about the invalid account operation.
     */
    public InvalidAccountException(String message) {
        super(message);
    }
}

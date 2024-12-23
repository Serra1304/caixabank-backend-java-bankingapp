package com.hackathon.finservice.Exception;

/**
 * Custom exception to indicate that a financial transaction cannot be completed
 * due to insufficient funds in the account.
 * <p>
 * This exception is thrown when a withdrawal, transfer, or any operation that
 * requires sufficient balance fails because the account does not have enough funds.
 * </p>
 *
 * <p>
 * It extends {@link RuntimeException}, allowing it to be used without explicit try-catch
 * blocks and enabling centralized exception handling mechanisms such as Spring's
 * {@link org.springframework.web.bind.annotation.ControllerAdvice}.
 * </p>
 *
 * <p>
 * Example scenarios where this exception might be thrown:
 * <ul>
 *     <li>A withdrawal amount exceeds the account balance.</li>
 *     <li>A transfer operation where the source account has insufficient funds.</li>
 * </ul>
 * </p>
 *
 * <p>
 * The exception should include a meaningful message to help identify the cause
 * and context of the error.
 * </p>
 */
public class InsufficientFundsException extends RuntimeException {

    /**
     * Constructs a new {@code InsufficientFundsException} with the specified detail message.
     *
     * @param message the detail message that provides additional context about the error.
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}

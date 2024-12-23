package com.hackathon.finservice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * GlobalExceptionHandler is a central exception handler that intercepts and handles
 * various exceptions thrown in the application, providing standardized responses.
 * It works in conjunction with Spring's @ControllerAdvice to globally manage exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException thrown when invalid arguments are provided.
     *
     * @param ex the IllegalArgumentException that was thrown
     * @return a ResponseEntity with a 400 Bad Request status and the exception message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles MethodArgumentNotValidException thrown when validation of method arguments fails.
     *
     * @param ex the MethodArgumentNotValidException that was thrown
     * @return a ResponseEntity with a 400 Bad Request status and the validation error message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * Handles InvalidPasswordException thrown when the provided password is invalid.
     *
     * @param ex the InvalidPasswordException that was thrown
     * @return a ResponseEntity with a 400 Bad Request status and the exception message
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles UserNotFoundException thrown when a user is not found in the system.
     *
     * @param ex the UserNotFoundException that was thrown
     * @return a ResponseEntity with a 400 Bad Request status and the exception message
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles InvalidCredentialsException thrown when invalid credentials are provided.
     *
     * @param ex the InvalidCredentialsException that was thrown
     * @return a ResponseEntity with a 401 Unauthorized status and the exception message
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    /**
     * Handles AccountNotFoundException thrown when an account is not found in the system.
     *
     * @param ex the AccountNotFoundException that was thrown
     * @return a ResponseEntity with a 404 Not Found status and the exception message
     */
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles InvalidAccountException thrown when an account is invalid.
     *
     * @param ex the InvalidAccountException that was thrown
     * @return a ResponseEntity with a 400 Bad Request status and the exception message
     */
    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<String> handleInvalidAccountException(InvalidAccountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    /**
     * Handles InsufficientFundsException thrown when there are insufficient funds for a transaction.
     *
     * @param ex the InsufficientFundsException that was thrown
     * @return a ResponseEntity with a 400 Bad Request status and a map containing the exception message
     */
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientFundsException(InsufficientFundsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("msg", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles InvalidTokenException thrown when the provided token is invalid.
     *
     * @param ex the InvalidTokenException that was thrown
     * @return a ResponseEntity with a 401 Unauthorized status and the exception message
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidTokenException(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}

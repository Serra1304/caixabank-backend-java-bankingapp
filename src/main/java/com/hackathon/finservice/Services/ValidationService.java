package com.hackathon.finservice.Services;

import com.hackathon.finservice.Exception.InvalidPasswordException;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service class for handling input validation tasks such as password strength
 * and email format validation. Centralizes validation logic to improve
 * reusability, maintainability, and adherence to separation of concerns.
 */
@Service
public class ValidationService {

    /**
     * Validates the strength of a password based on the following criteria:
     * <ul>
     *   <li>At least one uppercase letter.</li>
     *   <li>At least one lowercase letter.</li>
     *   <li>No whitespace characters.</li>
     *   <li>At least one digit.</li>
     *   <li>At least one special character.</li>
     * </ul>
     * If the password does not meet any of these criteria, an exception is thrown with the corresponding message.
     *
     * @param password the password to validate.
     * @throws InvalidPasswordException if the password does not meet the specified criteria.
     */
    public void validatePassword(String password) {
        Map<String, String> validationRules = new LinkedHashMap<>();

        // Validation rules
        validationRules.put(".*[A-Z].*", "Password must contain at least one uppercase letter");
        validationRules.put(".*[a-z].*", "Password must contain at least one lowercase letter");
        validationRules.put("^(?!.*\\s).*$", "Password cannot contain whitespace");
        validationRules.put("(.*[\\d\\W].*$)", "Password must contain at least one digit and one special character");
        validationRules.put(".*\\d.*", "Password must contain at least one digit character");
        validationRules.put(".*\\W.*", "Password must contain at least one special character");

        // Apply validation rules
        for (Map.Entry<String, String> rule : validationRules.entrySet()) {
            if (!password.matches(rule.getKey())) {
                throw new InvalidPasswordException(rule.getValue());
            }
        }
    }

    /**
     * Validates the format of an email address using a regular expression.
     * The email must conform to the following pattern:
     * <pre>
     * ^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$
     * </pre>
     * Examples of valid emails:
     * <ul>
     *   <li>example@example.com</li>
     *   <li>user.name@domain.co</li>
     *   <li>user_name@sub.domain.org</li>
     * </ul>
     *
     * @param email the email address to validate.
     * @throws IllegalArgumentException if the email format is invalid.
     */
    public void validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
    }
}


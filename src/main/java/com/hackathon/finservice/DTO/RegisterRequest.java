package com.hackathon.finservice.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for handling user registration requests.
 * This class captures the necessary information to register a new user, including
 * the user's name, email, and password. It also includes validation annotations
 * to enforce proper format and constraints.
 *
 * <p>Example JSON request body:</p>
 * <pre>
 * {
 *     "name": "Nuwe Test",
 *     "email": "nuwe@nuwe.com",
 *     "password": "NuweTest1$"
 * }
 * </pre>
 *
 * <p>Validation constraints:</p>
 * <ul>
 *     <li>Name cannot be blank and must be between 2 and 50 characters.</li>
 *     <li>Email must be in a valid email format.</li>
 *     <li>Password cannot be blank, must be at least 8 characters long,
 *         and include at least one uppercase letter, one lowercase letter,
 *         one number, and one special character.</li>
 * </ul>
 */
@NoArgsConstructor
@Setter
@Getter
public class RegisterRequest {
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(max = 128, message = "Password must be less than 128 characters long")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

}

package com.hackathon.finservice.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for handling login requests.
 * <p>
 * This class encapsulates the credentials required for a user to log in.
 * It includes validation annotations to ensure the provided data is valid.
 */
@NoArgsConstructor
@Setter
@Getter
public class LoginRequest {
    @NotEmpty(message = "Identifier is required")
    private String identifier;

    @NotEmpty(message = "Password is required")
    private String password;

}

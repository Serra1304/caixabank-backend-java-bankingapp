package com.hackathon.finservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for handling login responses.
 * <p>
 * This class encapsulates the response data sent back to the client after a successful login.
 * It contains the JWT token that will be used for subsequent authentication requests.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private String token;
}

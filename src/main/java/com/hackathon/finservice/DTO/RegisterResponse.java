package com.hackathon.finservice.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for sending user details in response to client requests.
 * This class contains information such as the user's name, email, account details,
 * and the hashed password.
 */
@NoArgsConstructor
@Getter
@Setter
public class RegisterResponse {
    private String name;
    private String email;
    private String accountNumber;
    private String accountType;
    private String hashedPassword;
}

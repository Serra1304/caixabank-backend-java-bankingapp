package com.hackathon.finservice.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the response containing a user's profile information.
 * This class holds details about a user's profile, including their name, email,
 * account number, account type, and hashed password.
 */
@NoArgsConstructor
@Getter
@Setter
public class UserProfileResponse {
    private String name;
    private String email;
    private String accountNumber;
    private String accountType;
    private String hashedPassword;
}

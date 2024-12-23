package com.hackathon.finservice.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a request to create or update an account.
 * This class includes validation annotations to ensure required fields are provided.
 */
@NoArgsConstructor
@Setter
@Getter
public class AccountRequest {

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotBlank(message = "Account type is required")
    private String accountType;

}

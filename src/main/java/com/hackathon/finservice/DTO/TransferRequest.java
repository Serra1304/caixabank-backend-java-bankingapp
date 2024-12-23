package com.hackathon.finservice.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a request to transfer funds between accounts.
 * This class includes the transaction amount and the target account number, with validation.
 */
@NoArgsConstructor
@Setter
@Getter
public class TransferRequest {
    private double amount;

    @NotEmpty
    private String targetAccountNumber;
}

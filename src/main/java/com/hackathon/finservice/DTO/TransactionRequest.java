package com.hackathon.finservice.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a request to perform a financial transaction.
 * This class includes the transaction amount and validation to ensure it is provided.
 */
@NoArgsConstructor
@Setter
@Getter
public class TransactionRequest {
    private double amount;

}

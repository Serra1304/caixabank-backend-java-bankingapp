package com.hackathon.finservice.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the response for a financial transaction in the system.
 * This class encapsulates details about a transaction, such as its identifier,
 * amount, type, status, date, and involved account numbers.
 */
@NoArgsConstructor
@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private Double amount;
    private String transactionType;
    private String transactionStatus;
    private Long transactionDate;
    private String sourceAccountNumber;
    private String targetAccountNumber;
}

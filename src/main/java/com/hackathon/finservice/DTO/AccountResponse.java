package com.hackathon.finservice.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing the response for an account query.
 * This class provides account details such as account number, balance, and account type.
 */
@NoArgsConstructor
@Getter
@Setter
public class AccountResponse {
    private String accountNumber;
    private double balance;
    private String accountType;


}

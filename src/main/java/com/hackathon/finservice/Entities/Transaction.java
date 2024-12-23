package com.hackathon.finservice.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Represents a financial transaction between accounts.
 * This entity captures details such as transaction amount, type, status,
 * involved accounts, and the transaction date.
 */
@Getter
@Setter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "target_account_id")
    private Account targetAccount;

    private LocalDateTime transactionDate;

    /**
     * Default constructor initializing default values for the transaction.
     * The transaction status is set to PENDING, and the transaction date
     * is set to the current timestamp.
     */
    public Transaction() {
        this.transactionStatus = TransactionStatus.PENDING;
        this.transactionDate = LocalDateTime.now();
    }

    /**
     * Retrieves the transaction date as a Unix timestamp in milliseconds.
     *
     * @return the transaction date in milliseconds since the epoch.
     */
    @JsonProperty("transactionDate")
    public long getTransactionDateAsTimestamp() {
        return transactionDate.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
    }
}
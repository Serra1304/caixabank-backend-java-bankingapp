package com.hackathon.finservice.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Represents a log entry for actions performed on an investment account.
 * Each log records the action, timestamp, and the associated account.
 */
@Entity
public class InvestmentAccountLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    /**
     * Default constructor for the InvestmentAccountLog class.
     */
    public InvestmentAccountLog() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructs a new InvestmentAccountLog with the specified action and associated account.
     *
     * @param action  the action performed on the investment account
     * @param account the account associated with the action
     */
    public InvestmentAccountLog(String action, Account account) {
        this.timestamp = LocalDateTime.now();
        this.action = action;
        this.account = account;
    }

    /**
     * Gets the unique identifier of the log entry.
     *
     * @return the log entry ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the log entry.
     *
     * @param id the log entry ID
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Gets the description of the action performed.
     *
     * @return the action description
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the description of the action performed.
     *
     * @param action the action description
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Gets the timestamp when the action occurred.
     *
     * @return the timestamp of the action
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the action occurred.
     *
     * @param timestamp the timestamp of the action
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets the account associated with the log entry.
     *
     * @return the associated account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets the account associated with the log entry.
     *
     * @param account the associated account
     */
    public void setAccount(Account account) {
        this.account = account;
    }
}

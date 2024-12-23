package com.hackathon.finservice.Repositories;

import com.hackathon.finservice.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing `Transaction` entities.
 * Extends Spring Data JPA's `JpaRepository` to provide CRUD operations
 * and custom queries for the `Transaction` entity.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Finds transactions where either the source account or the target account matches the given account number.
     *
     * @param sourceAccountNumber the account number of the source account.
     * @param targetAccountNumber the account number of the target account.
     * @return a list of transactions matching the criteria.
     */
    List<Transaction> findBySourceAccount_AccountNumberOrTargetAccount_AccountNumber(String sourceAccountNumber, String targetAccountNumber);

    /**
     * Finds recent transactions between a specific source and target account, filtered by a given date.
     *
     * @param sourceAccountNumber the account number of the source account.
     * @param targetAccountNumber the account number of the target account.
     * @param since               the start date from which to filter transactions.
     * @return a list of transactions matching the criteria.
     */
    @Query("SELECT t FROM Transaction t WHERE t.sourceAccount.accountNumber = :sourceAccountNumber " +
            "AND t.targetAccount.accountNumber = :targetAccountNumber " +
            "AND t.transactionDate > :since")
    List<Transaction> findRecentTransfers(String sourceAccountNumber, String targetAccountNumber, LocalDateTime since);
}


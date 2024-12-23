package com.hackathon.finservice.Services;

import com.hackathon.finservice.DTO.TransactionRequest;
import com.hackathon.finservice.DTO.TransactionResponse;
import com.hackathon.finservice.DTO.TransferRequest;
import com.hackathon.finservice.Entities.*;
import com.hackathon.finservice.Exception.InsufficientFundsException;
import com.hackathon.finservice.Mappers.TransactionMapper;
import com.hackathon.finservice.Repositories.AccountRepository;
import com.hackathon.finservice.Repositories.TransactionRepository;
import com.hackathon.finservice.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for handling transactions including deposits, withdrawals, and transfers.
 * <p>
 * This service handles transaction creation, processing, and fraud detection rules.
 * It also provides methods to retrieve transaction history for the authenticated user.
 * </p>
 */
@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    /**
     * Processes a deposit transaction.
     *
     * @param request the deposit request containing the amount to deposit.
     */
    public void deposit(TransactionRequest request) {
        double amount = request.getAmount();
        double finalAmount = amount >= 50000 ? amount * 0.98 : amount;       // > 50.000 apply 2% commission

        // Transaction with the final amount
        Transaction transaction = new Transaction();
        transaction.setAmount(finalAmount);
        transaction.setTransactionType(TransactionType.CASH_DEPOSIT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        // Save Transaction
        Account mainAccount = getMainAccountForUser();
        transaction.setTargetAccount(mainAccount);
        transactionRepository.save(transaction);

        // Update balance
        mainAccount.setBalance(mainAccount.getBalance() + finalAmount);
        accountRepository.save(mainAccount);

        // Update status transaction
        transaction.setTransactionStatus(TransactionStatus.APPROVED);
        transactionRepository.save(transaction);
    }

    /**
     * Processes a withdrawal transaction.
     *
     * @param request the withdrawal request containing the amount to withdraw.
     * @throws InsufficientFundsException if the account balance is insufficient.
     */
    public void withdraw(TransactionRequest request) {
        double amount = request.getAmount();
        double finalAmount = amount >= 10000 ? amount * 1.01 : amount;       // > 10.000 apply 1% commission
        Account mainAccount = getMainAccountForUser();

        verifySufficientAccountBalance(amount);

        // Transaction with the final amount
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.CASH_WITHDRAWAL);
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transaction.setSourceAccount(mainAccount);

        // Save transaction
        transactionRepository.save(transaction);

        // Update balance
        mainAccount.setBalance(mainAccount.getBalance() - finalAmount);
        accountRepository.save(mainAccount);

        // Update transaction
        transaction.setTransactionStatus(TransactionStatus.APPROVED);
        transactionRepository.save(transaction);
    }

    /**
     * Processes a transfer transaction between accounts.
     *
     * @param request the transfer request containing the target account and amount.
     * @throws InsufficientFundsException if the account balance is insufficient.
     * @throws IllegalArgumentException   if the target account is not found.
     */
    public void transfer(TransferRequest request) {
        double amount = request.getAmount();
        Account mainAccount = getMainAccountForUser();

        verifySufficientAccountBalance(amount);

        // Verify target account
        Account targetAccount = accountRepository.findByAccountNumber(request.getTargetAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));

        // Transaction with the final amount
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.CASH_TRANSFER);
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transaction.setSourceAccount(mainAccount);
        transaction.setTargetAccount(targetAccount);

        transactionRepository.save(transaction);

        // Verify fraud rules and save transactions
        List<Transaction> fraudulentTransactions = detectFrequentTransfers(request.getTargetAccountNumber());
        if (amount >= 80000 || !fraudulentTransactions.isEmpty()) {
            fraudulentTransactions.add(transaction);
            markTransactionsAsFraud(fraudulentTransactions);
        } else {
            // Update transaction
            transaction.setTransactionStatus(TransactionStatus.APPROVED);
        }
        // Update Balance
        mainAccount.setBalance(mainAccount.getBalance() - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);

        // Save Balance
        accountRepository.save(mainAccount);
        accountRepository.save(targetAccount);

        // Save transaction
        transactionRepository.save(transaction);
    }

    /**
     * Retrieves the transaction history for the authenticated user.
     *
     * @return a list of {@link TransactionResponse} objects representing the transaction history.
     */
    public List<TransactionResponse> getTransactionHistory() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transactionMapper::toTransactionResponse)
                .toList();
    }

    /**
     * Retrieves the main account for the authenticated user.
     *
     * @return the main {@link Account} of the authenticated user.
     * @throws IllegalArgumentException if the user is not found.
     */
    private Account getMainAccountForUser() {
        String authenticatedEmail = getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(authenticatedEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user.getMainAccount();
    }

    /**
     * Retrieves the email of the authenticated user.
     *
     * @return the email of the authenticated user.
     */
    private String getAuthenticatedUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    /**
     * Detects frequent transfers to a specific target account in the last 5 seconds.
     *
     * @param targetAccountNumber the account number of the target account.
     * @return a list of {@link Transaction} objects representing recent transfers.
     */
    private List<Transaction> detectFrequentTransfers(String targetAccountNumber) {
        LocalDateTime fiveSecondsAgo = LocalDateTime.now().minusSeconds(5);
        return transactionRepository.findRecentTransfers(
                getMainAccountForUser().getAccountNumber(), targetAccountNumber, fiveSecondsAgo);
    }

    /**
     * Marks the given transactions as fraud.
     *
     * @param transactions a list of {@link Transaction} objects to mark as fraud.
     */
    private void markTransactionsAsFraud(List<Transaction> transactions) {
        for (Transaction t : transactions) {
            t.setTransactionStatus(TransactionStatus.FRAUD);
            transactionRepository.save(t);
        }
    }

    /**
     * Verifies if the main account has sufficient balance for a transaction.
     *
     * @param amount the amount to verify.
     * @throws InsufficientFundsException if the balance is insufficient.
     */
    private void verifySufficientAccountBalance(double amount) {
        Account mainAccount = getMainAccountForUser();
        if (mainAccount.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient balance");
        }
    }
}


package com.hackathon.finservice.Services;

import com.hackathon.finservice.DTO.AccountResponse;
import com.hackathon.finservice.Entities.Account;
import com.hackathon.finservice.Entities.InvestmentAccountLog;
import com.hackathon.finservice.Entities.User;
import com.hackathon.finservice.Exception.AccountNotFoundException;
import com.hackathon.finservice.Exception.InvalidAccountException;
import com.hackathon.finservice.Mappers.AccountMapper;
import com.hackathon.finservice.Repositories.AccountRepository;
import com.hackathon.finservice.Repositories.InvestmentAccountLogRepository;
import com.hackathon.finservice.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing account-related operations.
 * <p>
 * This service provides functionality to create accounts, fetch account details, and validate users.
 * It integrates with repositories for persistence and uses {@link AccountMapper} for mapping entities to DTOs.
 * </p>
 */
@Service
@AllArgsConstructor
public class AccountService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private InvestmentAccountLogRepository investmentAccountLogRepository;

    /**
     * Creates a new account for the authenticated user.
     *
     * @param accountNumber the number of the main account required for validation.
     * @param accountType   the type of the new account to be created.
     * @throws InvalidAccountException if the main account is not valid.
     */
    public void createNewAccount(String accountNumber, String accountType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = validateUser(userEmail);

        Account mainAccount = user.getMainAccount();
        if (mainAccount == null || !mainAccount.getAccountNumber().equals(accountNumber)) {
            throw new InvalidAccountException("Main account is required to create a new account");
        }

        String uniqueAccountNumber = generateUniqueAccountNumber();
        Account newAccount = new Account();
        newAccount.setAccountNumber(uniqueAccountNumber);
        newAccount.setUser(user);
        newAccount.setAccountType(accountType);
        newAccount.setBalance(0.0);
        accountRepository.save(newAccount);

        if ("Invest".equals(accountType)) {
            investmentAccountLogRepository.save(
                    new InvestmentAccountLog("Investment Account Created", newAccount));
        }
    }

    /**
     * Generates a unique account number.
     *
     * @return a unique alphanumeric string.
     */
    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = RandomStringUtils.randomAlphanumeric(6).toLowerCase();
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    /**
     * Retrieves an account by its index for a given user email.
     *
     * @param email the email of the user.
     * @param index the index of the account in the user's account list.
     * @return the account at the specified index.
     * @throws AccountNotFoundException if the index is invalid.
     */
    public Account getAccountByIndex(String email, int index) {
        User user = validateUser(email);
        List<Account> accounts = accountRepository.findByUserId(user.getId());

        if (index < 0 || index >= accounts.size()) {
            throw new AccountNotFoundException("Account not found for the given index: " + index);
        }
        return accounts.get(index);
    }

    /**
     * Retrieves the main account for a given user email.
     *
     * @param email the email of the user.
     * @return the main account of the user.
     */
    public Account getMainAccountByEmail(String email) {
        User user = validateUser(email);
        return user.getMainAccount();
    }

    /**
     * Validates and retrieves a user by their email.
     *
     * @param email the email of the user.
     * @return the user entity.
     * @throws IllegalArgumentException if the user is not found.
     */
    private User validateUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found for the given identifier: " + email));
    }

    /**
     * Retrieves the main account response DTO for a given user email.
     *
     * @param email the email of the user.
     * @return the {@link AccountResponse} DTO of the main account.
     */
    public AccountResponse getMainAccountResponseByEmail(String email) {
        Account mainAccount = getMainAccountByEmail(email);
        return accountMapper.toAccountResponse(mainAccount);
    }

    /**
     * Retrieves the account response DTO for a specific account index.
     *
     * @param email the email of the user.
     * @param index the index of the account.
     * @return the {@link AccountResponse} DTO of the account.
     */
    public AccountResponse getAccountResponseByIndex(String email, int index) {
        Account account = getAccountByIndex(email, index);
        return accountMapper.toAccountResponse(account);
    }
}

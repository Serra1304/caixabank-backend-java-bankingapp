package com.hackathon.finservice.Repositories;

import com.hackathon.finservice.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing `Account` entities.
 * Extends Spring Data JPA's `JpaRepository` to provide CRUD operations
 * and additional query methods for working with `Account` entities.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Retrieves an account by its account number.
     *
     * @param accountNumber the account number to search for.
     * @return an {@link Optional} containing the account if found, or empty otherwise.
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * Retrieves all accounts associated with a specific user.
     *
     * @param userId the ID of the user whose accounts are to be retrieved.
     * @return a list of accounts belonging to the specified user.
     */
    List<Account> findByUserId(Long userId);

    /**
     * Retrieves all accounts of a specific type.
     *
     * @param type the type of accounts to retrieve (e.g., "savings", "checking").
     * @return a list of accounts matching the specified type.
     */
    List<Account> findAllByAccountType(String type);

    /**
     * Checks if an account exists with the specified account number.
     *
     * @param accountNumber the account number to check for existence.
     * @return {@code true} if an account with the specified number exists, {@code false} otherwise.
     */
    boolean existsByAccountNumber(String accountNumber);
}

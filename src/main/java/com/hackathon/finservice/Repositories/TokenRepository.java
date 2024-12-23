package com.hackathon.finservice.Repositories;

import com.hackathon.finservice.Entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing `Token` entities.
 * Extends Spring Data JPA's `JpaRepository` to provide CRUD operations
 * for the `Token` entity.
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    /**
     * Retrieves a `Token` entity by its token value.
     *
     * @param token the token value to search for.
     * @return an `Optional` containing the `Token` entity if found, or empty if not found.
     */
    Optional<Token> findByToken(String token);
}

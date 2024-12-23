package com.hackathon.finservice.Repositories;

import com.hackathon.finservice.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing `User` entities.
 * Extends Spring Data JPA's `JpaRepository` to provide CRUD operations
 * and custom queries for the `User` entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user.
     * @return an `Optional` containing the user if found, or empty if no user exists with the given email.
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a user exists with the given email address.
     *
     * @param email the email address to check.
     * @return `true` if a user exists with the given email, `false` otherwise.
     */
    boolean existsByEmail(String email);
}

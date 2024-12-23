package com.hackathon.finservice.Repositories;

import com.hackathon.finservice.Entities.InvestmentAccountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing `InvestmentAccountLog` entities.
 * Extends Spring Data JPA's `JpaRepository` to provide CRUD operations
 * for the `InvestmentAccountLog` entity.
 */
@Repository
public interface InvestmentAccountLogRepository extends JpaRepository<InvestmentAccountLog, Long> {
}

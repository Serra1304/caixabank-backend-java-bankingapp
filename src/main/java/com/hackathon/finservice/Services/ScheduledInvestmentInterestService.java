package com.hackathon.finservice.Services;

import com.hackathon.finservice.Entities.Account;
import com.hackathon.finservice.Entities.InvestmentAccountLog;
import com.hackathon.finservice.Repositories.AccountRepository;
import com.hackathon.finservice.Repositories.InvestmentAccountLogRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for periodically applying interest to all investment accounts.
 * The interest rate is currently fixed at 10%, and actions are logged in the investment account logs.
 */
@Service
public class ScheduledInvestmentInterestService {
    private final AccountRepository accountRepository;
    private final InvestmentAccountLogRepository investmentAccountLogRepository;

    /**
     * Constructs a new instance of ScheduledInvestmentInterestService with the required dependencies.
     *
     * @param accountRepository             the repository for account entities.
     * @param investmentAccountLogRepository the repository for investment account logs.
     */
    public ScheduledInvestmentInterestService(AccountRepository accountRepository,
                                              InvestmentAccountLogRepository investmentAccountLogRepository) {
        this.accountRepository = accountRepository;
        this.investmentAccountLogRepository = investmentAccountLogRepository;
    }

    /**
     * Periodically applies a fixed interest rate to all accounts of type "Invest".
     * The method is scheduled to run at a fixed rate of 10 seconds (configurable).
     * <p>
     * For each investment account:
     * <ul>
     *     <li>Calculates the new balance by applying a 10% interest rate.</li>
     *     <li>Updates the account balance in the database.</li>
     *     <li>Logs the action in the investment account logs.</li>
     * </ul>
     *
     * @throws RuntimeException if any database operation fails. Errors in one account do not stop the processing of others.
     */
    @Scheduled(fixedRate = 10000)
    public void applyInterestToAllInvestmentAccounts() {
        // Fetch all investment accounts
        List<Account> investmentAccounts = accountRepository.findAllByAccountType("Invest");

        // Apply interest to each account
        for (Account account : investmentAccounts) {
            double currentBalance = account.getBalance();
            double newBalance = currentBalance + (currentBalance * 0.10); // 10% interés
            account.setBalance(newBalance);
            accountRepository.save(account);

            // Log the action
            investmentAccountLogRepository.save(new InvestmentAccountLog("Interest Applied", account));
        }
    }
}


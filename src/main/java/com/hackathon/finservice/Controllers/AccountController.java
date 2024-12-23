package com.hackathon.finservice.Controllers;

import com.hackathon.finservice.DTO.*;
import com.hackathon.finservice.Services.AccountService;
import com.hackathon.finservice.Services.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class that handles account-related operations for users.
 * It provides endpoints for creating accounts, depositing, withdrawing funds,
 * transferring funds, and retrieving transaction history.
 */
@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;
    private TransactionService transactionService;

    /**
     * Creates a new account for the user based on the provided account details.
     *
     * @param accountRequest the request body containing account details (account number and type)
     * @return a response entity with a success message
     */
    @PostMapping("/create")
    public ResponseEntity<String> createNewAccount(@RequestBody @Valid AccountRequest accountRequest) {
        accountService.createNewAccount(accountRequest.getAccountNumber(), accountRequest.getAccountType());
        return ResponseEntity.ok("New account added successfully for user");
    }

    /**
     * Deposits cash into the user's account based on the provided transaction request.
     *
     * @param request the request body containing the deposit transaction details
     * @return a response entity with a success message
     */
    @PostMapping("/deposit")
    public ResponseEntity<SimpleMessageResponse> deposit(@RequestBody TransactionRequest request) {
        transactionService.deposit(request);
        return ResponseEntity.ok(new SimpleMessageResponse("Cash deposited successfully"));
    }

    /**
     * Withdraws cash from the user's account based on the provided transaction request.
     *
     * @param request the request body containing the withdrawal transaction details
     * @return a response entity with a success message
     */
    @PostMapping("/withdraw")
    public ResponseEntity<SimpleMessageResponse> withdraw(@RequestBody TransactionRequest request) {
        transactionService.withdraw(request);
        return ResponseEntity.ok(new SimpleMessageResponse("Cash withdrawn successfully"));
    }

    /**
     * Transfers funds from one account to another based on the provided transfer request.
     *
     * @param request the request body containing the transfer transaction details
     * @return a response entity with a success message
     */
    @PostMapping("/fund-transfer")
    public ResponseEntity<SimpleMessageResponse> transfer(@RequestBody TransferRequest request) {
        transactionService.transfer(request);
        return ResponseEntity.ok(new SimpleMessageResponse("Fund transferred successfully"));
    }

    /**
     * Retrieves the transaction history for the user.
     *
     * @return a response entity containing a list of transaction responses
     */
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponse>> getHistory() {
        return ResponseEntity.ok(transactionService.getTransactionHistory());
    }
}


package com.hackathon.finservice.Controllers;

import com.hackathon.finservice.DTO.AccountResponse;
import com.hackathon.finservice.DTO.UserProfileResponse;
import com.hackathon.finservice.Services.AccountService;
import com.hackathon.finservice.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.hackathon.finservice.Util.SecurityUtil.getAuthenticatedEmail;

/**
 * Controller class that handles requests related to the user dashboard.
 * It provides endpoints for retrieving the user's profile and account information.
 */
@Controller
@RequestMapping("/api/dashboard")
@AllArgsConstructor
public class DashboardController {
    private UserService userService;
    private AccountService accountService;

    /**
     * Retrieves the profile information of the authenticated user.
     * This endpoint fetches the user details based on the authenticated user's email.
     *
     * @return a response entity containing the user's profile information
     */
    @GetMapping("/user")
    public ResponseEntity<UserProfileResponse> getUserProfile() {
        String email = getAuthenticatedEmail();
        UserProfileResponse response = userService.getUserProfileResponseByEmail(email);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves the main account information of the authenticated user.
     * This endpoint fetches the main account details based on the authenticated user's email.
     *
     * @return a response entity containing the main account information
     */
    @GetMapping("/account")
    public ResponseEntity<AccountResponse> getMainAccountInfo() {
        String email = getAuthenticatedEmail();
        AccountResponse response = accountService.getMainAccountResponseByEmail(email);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves account information for a specific account by index.
     * This endpoint fetches the account details for the given index of the authenticated user.
     *
     * @param index the index of the account
     * @return a response entity containing the account information for the given index
     */
    @GetMapping("/account/{index}")
    public ResponseEntity<AccountResponse> getAccountByIndex(@PathVariable int index) {
        String email = getAuthenticatedEmail();
        AccountResponse response = accountService.getAccountResponseByIndex(email, index);
        return ResponseEntity.ok(response);
    }
}

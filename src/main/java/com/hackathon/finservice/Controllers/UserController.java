package com.hackathon.finservice.Controllers;

import com.hackathon.finservice.DTO.LoginRequest;
import com.hackathon.finservice.DTO.LoginResponse;
import com.hackathon.finservice.DTO.RegisterRequest;
import com.hackathon.finservice.DTO.RegisterResponse;
import com.hackathon.finservice.Services.AuthService;
import com.hackathon.finservice.Services.UserService;
import com.hackathon.finservice.Util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class responsible for handling user authentication and registration.
 * It provides endpoints for user registration, login, and logout operations.
 */
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private AuthService authService;
    private JwtUtil jwtUtil;

    /**
     * Registers a new user.
     * This endpoint allows the creation of a new user with the provided registration details.
     *
     * @param request the registration request containing user details
     * @return a response entity containing the registration response with user details
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Logs in an existing user.
     * This endpoint validates the user credentials and generates a JWT token for authentication.
     *
     * @param loginRequest the login request containing user credentials (identifier and password)
     * @return a response entity containing the JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getIdentifier(), loginRequest.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    /**
     * Logs out the authenticated user.
     * This endpoint invalidates the current JWT token, effectively logging out the user.
     *
     * @param authorizationHeader the Authorization header containing the JWT token
     * @return a response entity with a success message indicating the user has logged out
     */
    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = jwtUtil.extractJwtToken(authorizationHeader);
        authService.logout(jwtToken);
        return ResponseEntity.ok("Successfully logged out");
    }
}


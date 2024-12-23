package com.hackathon.finservice.Services;

import com.hackathon.finservice.DTO.RegisterRequest;
import com.hackathon.finservice.DTO.RegisterResponse;
import com.hackathon.finservice.DTO.UserProfileResponse;
import com.hackathon.finservice.Entities.Account;
import com.hackathon.finservice.Entities.User;
import com.hackathon.finservice.Mappers.RegisterMapper;
import com.hackathon.finservice.Mappers.UserMapper;
import com.hackathon.finservice.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class responsible for managing user-related operations,
 * including registration, retrieval, and user profile generation.
 */
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RegisterMapper registerMapper;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;

    /**
     * Registers a new user based on the given request.
     *
     * @param request the registration request containing the user's name, email, and password.
     * @return a response object containing user registration details.
     * @throws IllegalArgumentException if the email already exists or the validation fails.
     */
    public RegisterResponse registerUser(RegisterRequest request) {
        validateRequest(request);

        String hashedPassword = hashPassword(request.getPassword());
        User user = createUserEntity(request, hashedPassword);
        Account mainAccount = createMainAccount(user);
        user.setMainAccount(mainAccount);

        userRepository.save(user);

        return mapToRegisterResponse(user);
    }

    /**
     * Validates the registration request by ensuring the email is valid,
     * does not already exist, and the password meets security requirements.
     *
     * @param request the registration request to validate.
     * @throws IllegalArgumentException if the email is invalid, already exists,
     *                                  or the password does not meet the requirements.
     */
    private void validateRequest(RegisterRequest request) {
        validationService.validateEmail(request.getEmail());
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        validationService.validatePassword(request.getPassword());
    }

    /**
     * Hashes a plaintext password.
     *
     * @param password the plaintext password to hash.
     * @return the hashed version of the password.
     */
    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Creates a user entity based on the provided registration request and hashed password.
     *
     * @param request        the registration request containing the user's details.
     * @param hashedPassword the hashed password to assign to the user.
     * @return a new {@link User} entity.
     */
    private User createUserEntity(RegisterRequest request, String hashedPassword) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setHashedPassword(hashedPassword);
        return user;
    }

    /**
     * Creates a main account for the specified user.
     *
     * @param user the user for whom the main account will be created.
     * @return a new {@link Account} entity.
     */
    private Account createMainAccount(User user) {
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType("Main");
        account.setUser(user);
        return account;
    }

    /**
     * Generates a random account number.
     *
     * @return a randomly generated account number.
     */
    private String generateAccountNumber() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email of the user to retrieve.
     * @return the {@link User} entity associated with the given email.
     * @throws IllegalArgumentException if no user is found with the specified email.
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    /**
     * Retrieves a user's profile by their email address.
     *
     * @param email the email of the user whose profile is to be retrieved.
     * @return a {@link UserProfileResponse} containing the user's profile details.
     * @throws IllegalArgumentException if no user is found with the specified email.
     */
    public UserProfileResponse getUserProfileResponseByEmail(String email) {
        User user = getUserByEmail(email);

        return userMapper.toUserProfileResponse(user);
    }

    /**
     * Maps a user entity to a registration response.
     *
     * @param user the user entity to map.
     * @return a {@link RegisterResponse} containing the user's registration details.
     */
    private RegisterResponse mapToRegisterResponse(User user) {
        return registerMapper.toRegisterResponse(user);
    }
}

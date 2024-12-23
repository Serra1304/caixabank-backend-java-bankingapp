package com.hackathon.finservice.Services;

import com.hackathon.finservice.Entities.User;
import com.hackathon.finservice.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Implementation of the {@link UserDetailsService} interface for loading user-specific data.
 * <p>
 * This service is used by Spring Security to authenticate users based on their email and hashed password.
 * </p>
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Locates the user based on the email. This method is called by Spring Security during authentication.
     *
     * @param email the email identifying the user whose data is required.
     * @return a fully populated {@link UserDetails} object for the user.
     * @throws UsernameNotFoundException if the user could not be found or the user has no granted authorities.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for the given identifier: " + email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getHashedPassword(), new ArrayList<>());
    }
}


package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.security.JwtUtil;
import dev.lebenkov.libra.api.util.exception.UserAlreadyExistsException;
import dev.lebenkov.libra.storage.dto.AuthResponse;
import dev.lebenkov.libra.storage.dto.UserRegistrationRequest;
import dev.lebenkov.libra.storage.model.User;
import dev.lebenkov.libra.storage.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationServiceImpl implements RegistrationService {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    JwtUtil jwtUtil;
    TokenService tokenService;
    AccountDetailsService accountDetailsService;

    private void checkExistingUser(String username) {
        userRepository.findByUsername(username)
                .ifPresent(existingAccount -> {
                    throw new UserAlreadyExistsException("User with username " + username + " already exists.");
                });
    }

    @Override
    @Transactional
    public AuthResponse register(UserRegistrationRequest userRegistrationRequest) {
        checkExistingUser(userRegistrationRequest.getUsername());

        User user = User.builder()
                .username(userRegistrationRequest.getUsername().toLowerCase())
                .email(userRegistrationRequest.getEmail())
                .password(passwordEncoder.encode(userRegistrationRequest.getPassword()))
                .role("ROLE_USER")
                .registrationDate(LocalDate.now())
                .build();

        User savedUser = userRepository.save(user);

        UserDetails userDetails = accountDetailsService.loadUserByUsername(user.getUsername());

        String jwtToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        tokenService.saveUserToken(savedUser, jwtToken);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
}

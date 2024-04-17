package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.security.JwtUtil;
import dev.lebenkov.libra.storage.dto.AuthRequest;
import dev.lebenkov.libra.storage.dto.AuthResponse;
import dev.lebenkov.libra.storage.model.User;
import dev.lebenkov.libra.storage.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    UserRepository userRepository;

    JwtUtil jwtUtil;
    TokenService tokenService;
    AccountDetailsService accountDetailsService;

    AuthenticationManager authenticationManager;

    private User checkUserExists(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username " + username + " not found"));
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        User account = checkUserExists(authRequest.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        UserDetails user = accountDetailsService.loadUserByUsername(authRequest.getUsername());

        String jwtToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        tokenService.revokeAllUserTokens(account);
        tokenService.saveUserToken(account, jwtToken);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
}

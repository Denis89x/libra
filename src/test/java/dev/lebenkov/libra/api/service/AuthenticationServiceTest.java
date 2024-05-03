package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.security.JwtUtil;
import dev.lebenkov.libra.storage.dto.AuthRequest;
import dev.lebenkov.libra.storage.dto.AuthResponse;
import dev.lebenkov.libra.storage.model.User;
import dev.lebenkov.libra.storage.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private TokenService tokenService;

    @Mock
    private AccountDetailsService accountDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        authenticationService = new AuthenticationServiceImpl(userRepository, jwtUtil, tokenService, accountDetailsService, authenticationManager);
    }

    @Test
    void AuthenticationService_Authenticate_ReturnsAuthResponse() {
        // Arrange
        AuthRequest authRequest = AuthRequest.builder()
                .username("username")
                .password("password")
                .build();

        UserDetails userDetails = mock(UserDetails.class);

        // Act
        when(userRepository.findByUsername(authRequest.getUsername())).thenReturn(Optional.of(User.builder().build()));
        when(accountDetailsService.loadUserByUsername(authRequest.getUsername())).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(userDetails)).thenReturn("refreshToken");

        AuthResponse response = authenticationService.authenticate(authRequest);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }
}
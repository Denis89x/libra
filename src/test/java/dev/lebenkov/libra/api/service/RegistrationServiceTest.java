package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.security.JwtUtil;
import dev.lebenkov.libra.storage.dto.AuthResponse;
import dev.lebenkov.libra.storage.dto.UserRegistrationRequest;
import dev.lebenkov.libra.storage.model.User;
import dev.lebenkov.libra.storage.repository.TokenRepository;
import dev.lebenkov.libra.storage.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private TokenService tokenService;

    @Mock
    private AccountDetailsService accountDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        registrationService = new RegistrationServiceImpl(jwtUtil, tokenService, userRepository, passwordEncoder, accountDetailsService);
    }

    @Test
    void RegistrationService_Register_ReturnsAuthResponse() {
        // Arrange
        User user = User.builder()
                .username("testusername")
                .email("testEmail@mail.ru")
                .password("testPassword")
                .role("ROLE_USER")
                .registrationDate(LocalDate.now())
                .build();

        UserRegistrationRequest userRegistrationRequest = UserRegistrationRequest.builder()
                .username("testusername")
                .password("testPassword")
                .email("testEmail@mail.ru")
                .build();

        UserDetails userDetails = mock(UserDetails.class);

        // Act
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(accountDetailsService.loadUserByUsername(user.getUsername())).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(userDetails)).thenReturn("refreshToken");

        AuthResponse response = registrationService.register(userRegistrationRequest);

        // Assert
        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }
}
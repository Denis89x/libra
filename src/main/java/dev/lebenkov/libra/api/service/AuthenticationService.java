package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.AuthRequest;
import dev.lebenkov.libra.storage.dto.AuthResponse;

public interface AuthenticationService {
    AuthResponse authenticate(AuthRequest authRequest);
}

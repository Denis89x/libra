package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.AuthResponse;
import dev.lebenkov.libra.storage.dto.UserRegistrationRequest;

public interface RegistrationService {
    AuthResponse register(UserRegistrationRequest userRegistrationRequest);
}

package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.model.User;
import dev.lebenkov.libra.storage.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionUserProviderServiceImpl implements SessionUserProviderService {

    UserRepository userRepository;

    @Override
    public User getUserFromSession() {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }
}

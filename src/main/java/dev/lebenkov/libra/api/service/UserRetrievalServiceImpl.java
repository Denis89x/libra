package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.util.exception.ObjectNotFoundException;
import dev.lebenkov.libra.storage.model.User;
import dev.lebenkov.libra.storage.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRetrievalServiceImpl implements UserRetrievalService {

    UserRepository userRepository;

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User with " + id + " id not found"));
    }
}

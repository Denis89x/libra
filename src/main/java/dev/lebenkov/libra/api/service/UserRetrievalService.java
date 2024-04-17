package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.model.User;

public interface UserRetrievalService {
    User findUserById(Long id);
}

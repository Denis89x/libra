package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.model.Message;

public interface MessageRetrievalService {
    Message findMessageById(Long messageId);
}

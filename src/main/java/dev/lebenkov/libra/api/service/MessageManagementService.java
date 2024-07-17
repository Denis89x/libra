package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.MessageChangeRequest;
import dev.lebenkov.libra.storage.dto.MessageRequest;

public interface MessageManagementService {
    void changeMessage(long messageId, MessageChangeRequest messageChangeRequest);

    void createMessage(MessageRequest messageRequest);

    void deleteMessage(long messageId);
}
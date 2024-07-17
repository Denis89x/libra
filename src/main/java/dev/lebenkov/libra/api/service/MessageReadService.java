package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.MessageResponse;

import java.util.List;

public interface MessageReadService {

    List<MessageResponse> fetchAllMessagesBetweenReceiverAndSender(Long receiverId, Long senderId);
}
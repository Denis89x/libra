package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.util.exception.ObjectNotFoundException;
import dev.lebenkov.libra.storage.model.Message;
import dev.lebenkov.libra.storage.repository.MessageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageRetrievalServiceImpl implements MessageRetrievalService {

    MessageRepository messageRepository;
    SessionUserProviderService sessionUserProviderService;

    @Override
    public Message findMessageById(Long messageId) {
        return messageRepository.findByMessageIdAndSender_UserId(messageId, sessionUserProviderService.getUserFromSession().getUserId())
                .orElseThrow(() -> new ObjectNotFoundException("Message with id " + messageId + " not found"));
    }
}
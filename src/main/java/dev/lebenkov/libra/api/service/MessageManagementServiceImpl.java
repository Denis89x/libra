package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.MessageChangeRequest;
import dev.lebenkov.libra.storage.dto.MessageRequest;
import dev.lebenkov.libra.storage.model.Message;
import dev.lebenkov.libra.storage.repository.MessageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageManagementServiceImpl implements MessageManagementService {

    MessageRepository messageRepository;

    MessageRetrievalService messageRetrievalService;
    SessionUserProviderService sessionUserProviderService;
    UserRetrievalService userRetrievalService;

    private Message createMessageEntity(MessageRequest messageRequest) {
        return Message.builder()
                .messageText(messageRequest.getMessageText())
                .sendDate(LocalDate.now())
                .receiver(userRetrievalService.findUserById(messageRequest.getReceiverId()))
                .sender(sessionUserProviderService.getUserFromSession())
                .isEdited(false)
                .build();
    }

    private Message changeMessage(Message message, MessageChangeRequest messageChangeRequest) {
        message.setMessageText(messageChangeRequest.getMessageText());
        message.setSendDate(LocalDate.now());
        message.setIsEdited(true);

        return message;
    }

    @Override
    public void createMessage(MessageRequest messageRequest) {
        Message message = createMessageEntity(messageRequest);

        messageRepository.save(message);
    }

    @Override
    public void deleteMessage(long messageId) {
        messageRetrievalService.findMessageById(messageId);

        messageRepository.deleteByMessageIdAndSender_UserId(messageId, sessionUserProviderService.getUserFromSession().getUserId());
    }

    @Override
    public void changeMessage(long messageId, MessageChangeRequest messageChangeRequest) {
        Message message = messageRetrievalService.findMessageById(messageId);

        messageRepository.save(changeMessage(message, messageChangeRequest));
    }
}
package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.MessageResponse;
import dev.lebenkov.libra.storage.model.Message;
import dev.lebenkov.libra.storage.repository.MessageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageReadServiceImpl implements MessageReadService {

    MessageRepository messageRepository;

    ModelMapper modelMapper;

    private MessageResponse convertMessageToMessageResponse(Message message) {
        return modelMapper.map(message, MessageResponse.class);
    }

    @Override // TODO: Изменить метод, сообщения может просмотреть любой, кто знает два айдишника
    public List<MessageResponse> fetchAllMessagesBetweenReceiverAndSender(Long receiverId, Long senderId) {
        return messageRepository.findAllBySender_UserIdAndReceiver_UserId(senderId, receiverId).stream()
                .map(this::convertMessageToMessageResponse)
                .toList();
    }
}
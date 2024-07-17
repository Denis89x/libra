package dev.lebenkov.libra.api.controller;

import dev.lebenkov.libra.api.service.MessageManagementService;
import dev.lebenkov.libra.api.service.MessageReadService;
import dev.lebenkov.libra.storage.dto.MessageChangeRequest;
import dev.lebenkov.libra.storage.dto.MessageRequest;
import dev.lebenkov.libra.storage.dto.MessageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {

    MessageManagementService messageManagementService;
    MessageReadService messageReadService;

    @GetMapping
    public ResponseEntity<List<MessageResponse>> fetchAllMessageBetweenReceiverAndSender(
            @RequestParam("receiver_id") Long receiverId,
            @RequestParam("sender_id") Long senderId) {
        return new ResponseEntity<>(messageReadService.fetchAllMessagesBetweenReceiverAndSender(receiverId, senderId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody MessageRequest messageRequest) {
        messageManagementService.createMessage(messageRequest);
        return new ResponseEntity<>("Message was successfully created", HttpStatus.CREATED);
    }

    @PutMapping("/{message_id}")
    public ResponseEntity<String> updateMessage(
            @PathVariable("message_id") Long messageId,
            @RequestBody MessageChangeRequest messageChangeRequest) {
        messageManagementService.changeMessage(messageId, messageChangeRequest);
        return ResponseEntity.ok("Message was successfully updated!");
    }

    @DeleteMapping("/{message_id}")
    public ResponseEntity<String> deleteMessage(@PathVariable("message_id") Long messageId) {
        messageManagementService.deleteMessage(messageId);
        return ResponseEntity.ok("Message was successfully deleted!");
    }

    // TODO: Сделать метод для просмотра всех пользователей с которыми есть переписка
}
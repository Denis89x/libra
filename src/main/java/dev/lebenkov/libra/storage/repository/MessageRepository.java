package dev.lebenkov.libra.storage.repository;

import dev.lebenkov.libra.storage.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findByMessageIdAndSender_UserId(Long id, Long sender);

    void deleteByMessageIdAndSender_UserId(Long id, Long sender);

    List<Message> findAllBySender_UserIdAndReceiver_UserId(Long senderId, Long receiverId);
}
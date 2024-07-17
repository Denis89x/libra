package dev.lebenkov.libra.storage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "message")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {

    @Id
    @JsonProperty("message_id")
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long messageId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    User receiver;

    @JsonProperty("message_text")
    @Column(name = "message_text")
    String messageText;

    @JsonProperty("send_date")
    @Column(name = "send_date")
    LocalDate sendDate;

    @Column(name = "edited")
    Boolean isEdited;
}
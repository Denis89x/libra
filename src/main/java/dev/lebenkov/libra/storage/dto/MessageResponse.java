package dev.lebenkov.libra.storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {

    @JsonProperty("message_id")
    Long messageId;

    @JsonProperty("message_text")
    String messageText;

    @JsonProperty("send_date")
    LocalDate sendDate;

    @JsonProperty("is_edited")
    Boolean isEdited;
}
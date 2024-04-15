package dev.lebenkov.libra.storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradeRequestDto {

    @JsonProperty("trade_receiver_id")
    Long tradeReceiverId;

    @JsonProperty("book_sender_id")
    Long bookSenderId;

    @JsonProperty("book_receiver_id")
    Long bookReceiverId;
}
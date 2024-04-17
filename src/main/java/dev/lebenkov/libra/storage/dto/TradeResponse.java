package dev.lebenkov.libra.storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradeResponse {

    @JsonProperty("trade_id")
    Long tradeId;

    String status;

    @JsonProperty("sender_username")
    String senderUsername;

    @JsonProperty("sender_book_name")
    String senderBookName;

    @JsonProperty("receiver_book_name")
    String receiverBookName;
}
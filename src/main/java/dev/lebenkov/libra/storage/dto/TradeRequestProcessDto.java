package dev.lebenkov.libra.storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradeRequestProcessDto {
    @JsonProperty("trade_request_id")
    Long tradeRequestId;

    @JsonProperty("result_status")
    String resultStatus;
}
package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.TradeRequestProcessDto;
import dev.lebenkov.libra.storage.dto.TradeRequestDto;

public interface TradeRequestService {
    void sendTradeRequest(TradeRequestDto tradeRequestDto);

    void processTradeRequest(TradeRequestProcessDto tradeRequestProcessDto);
}

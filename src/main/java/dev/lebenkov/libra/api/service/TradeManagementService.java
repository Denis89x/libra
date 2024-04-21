package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.TradeProcess;
import dev.lebenkov.libra.storage.dto.TradeRequest;

public interface TradeManagementService {
    void sendTradeRequest(TradeRequest tradeRequest);

    void processTradeRequest(TradeProcess tradeProcess);
}
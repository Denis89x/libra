package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.TradeProcess;
import dev.lebenkov.libra.storage.dto.TradeRequest;
import dev.lebenkov.libra.storage.dto.TradeResponse;

import java.util.List;

public interface TradeService {
    void sendTradeRequest(TradeRequest tradeRequest);

    void processTradeRequest(TradeProcess tradeProcess);

    List<TradeResponse> getAllPendingTrades();

    TradeResponse getTrade(Long tradeId);
}

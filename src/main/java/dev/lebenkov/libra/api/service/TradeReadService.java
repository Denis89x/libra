package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.TradeResponse;

import java.util.List;

public interface TradeReadService {
    List<TradeResponse> getAllPendingTrades();

    List<TradeResponse> getAllAcceptedTrades();

    List<TradeResponse> getAllRejectedTrades();

    TradeResponse getTrade(Long tradeId);
}

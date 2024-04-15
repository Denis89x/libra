package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.model.TradeRequest;

public interface TradeRetrievalService {
    TradeRequest findTradeRequestById(Long tradeRequestId);
}

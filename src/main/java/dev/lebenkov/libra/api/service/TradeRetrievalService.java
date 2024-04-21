package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.model.Trade;

public interface TradeRetrievalService {
    Trade findTradeById(Long tradeRequestId);
}

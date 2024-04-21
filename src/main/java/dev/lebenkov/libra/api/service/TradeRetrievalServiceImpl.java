package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.util.exception.ObjectNotFoundException;
import dev.lebenkov.libra.storage.model.Trade;
import dev.lebenkov.libra.storage.repository.TradeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradeRetrievalServiceImpl implements TradeRetrievalService {

    TradeRepository tradeRepository;

    @Override
    public Trade findTradeById(Long tradeRequestId) {
        return tradeRepository.findById(tradeRequestId).
                orElseThrow(() -> new ObjectNotFoundException("TradeRequest with id " + tradeRequestId + " not found"));
    }
}
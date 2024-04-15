package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.util.exception.ObjectNotFoundException;
import dev.lebenkov.libra.storage.model.TradeRequest;
import dev.lebenkov.libra.storage.repository.TradeRequestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradeRetrievalServiceImpl implements TradeRetrievalService {

    TradeRequestRepository tradeRequestRepository;

    @Override
    public TradeRequest findTradeRequestById(Long tradeRequestId) {
        return tradeRequestRepository.findById(tradeRequestId).
                orElseThrow(() -> new ObjectNotFoundException("TradeRequest with id " + tradeRequestId + " not found"));
    }
}
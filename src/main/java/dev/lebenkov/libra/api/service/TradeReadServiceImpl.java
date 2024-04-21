package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.util.exception.ObjectNotFoundException;
import dev.lebenkov.libra.storage.dto.TradeResponse;
import dev.lebenkov.libra.storage.enums.TradeStatus;
import dev.lebenkov.libra.storage.model.Trade;
import dev.lebenkov.libra.storage.repository.TradeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradeReadServiceImpl implements TradeReadService {

    TradeRepository tradeRepository;

    SessionUserProviderService sessionUserProviderService;

    private TradeResponse convertTradeToTradeResponse(Trade trade) {
        return TradeResponse.builder()
                .tradeId(trade.getRequestId())
                .status(trade.getStatus())
                .senderUsername(trade.getTradeSender().getUsername())
                .senderBookName(trade.getBookSender().getTitle())
                .receiverBookName(trade.getBookReceiver().getTitle())
                .build();
    }

    private List<TradeResponse> getAllTradesByStatus(TradeStatus status) {
        return tradeRepository.findAllByTradeReceiverAndStatus(sessionUserProviderService.getUserFromSession(), status.name())
                .stream().map(this::convertTradeToTradeResponse).toList();
    }

    @Override
    public List<TradeResponse> getAllPendingTrades() {
        return getAllTradesByStatus(TradeStatus.Pending);
    }

    @Override
    public List<TradeResponse> getAllAcceptedTrades() {
        return getAllTradesByStatus(TradeStatus.Accepted);
    }

    @Override
    public List<TradeResponse> getAllRejectedTrades() {
        return getAllTradesByStatus(TradeStatus.Rejected);
    }

    @Override
    public TradeResponse getTrade(Long tradeId) {
        return convertTradeToTradeResponse(tradeRepository.findByRequestIdAndTradeReceiverOrTradeSender(
                        tradeId, sessionUserProviderService.getUserFromSession())
                .orElseThrow(() -> new ObjectNotFoundException("Trade not found")));
    }
}

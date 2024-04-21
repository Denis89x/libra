package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.TradeProcess;
import dev.lebenkov.libra.storage.dto.TradeRequest;
import dev.lebenkov.libra.storage.enums.TradeStatus;
import dev.lebenkov.libra.storage.model.Book;
import dev.lebenkov.libra.storage.model.Trade;
import dev.lebenkov.libra.storage.model.TradeHistory;
import dev.lebenkov.libra.storage.model.User;
import dev.lebenkov.libra.storage.repository.BookRepository;
import dev.lebenkov.libra.storage.repository.TradeHistoryRepository;
import dev.lebenkov.libra.storage.repository.TradeRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradeManagementServiceImpl implements TradeManagementService {

    TradeRepository tradeRepository;

    BookRepository bookRepository; // TODO: change logic

    UserRetrievalService userRetrievalService;
    BookRetrievalService bookRetrievalService;
    TradeRetrievalService tradeRetrievalService;
    SessionUserProviderService sessionUserProviderService;
    TradeHistoryRepository tradeHistoryRepository;

    private Trade createTradeRequestByTradeRequestDto(TradeRequest tradeRequest) {
        return Trade.builder()
                .status(TradeStatus.Pending.name())
                .tradeReceiver(userRetrievalService.findUserById((tradeRequest.getTradeReceiverId())))
                .tradeSender(sessionUserProviderService.getUserFromSession())
                .bookSender(
                        bookRetrievalService.getBookOwnedByUserById(tradeRequest.getBookSenderId(),
                                sessionUserProviderService.getUserFromSession().getUserId())
                )
                .bookReceiver(
                        bookRetrievalService.getBookOwnedByUserById(tradeRequest.getBookReceiverId(),
                                tradeRequest.getTradeReceiverId())
                )
                .build();
    }

    private Trade saveTradeRequest(Trade trade) {
        return tradeRepository.save(trade);
    }

    private TradeHistory createTradeHistoryByTradeRequestDto(TradeRequest tradeRequest) {
        return TradeHistory.builder()
                .tradeDate(LocalDate.now())
                .trade(saveTradeRequest(createTradeRequestByTradeRequestDto(tradeRequest)))
                .build();
    }

    private void saveTradeHistory(TradeHistory tradeHistory) {
        tradeHistoryRepository.save(tradeHistory);
    }

    @Override
    public void sendTradeRequest(TradeRequest tradeRequest) {
        saveTradeHistory(createTradeHistoryByTradeRequestDto(tradeRequest));
    }

    private void acceptTradeRequest(Trade trade) {
        swapBooksOwnersAndSave(trade);
        updateTradeRequestStatus(trade, TradeStatus.Accepted.name());
    }

    private void swapBooksOwnersAndSave(Trade trade) {
        Book receiverBook = trade.getBookReceiver();
        Book senderBook = trade.getBookSender();

        swapBookOwners(receiverBook, senderBook);
        saveBooks(List.of(receiverBook, senderBook));
    }

    private void swapBookOwners(Book receiverBook, Book senderBook) {
        User receiver = receiverBook.getUser();
        User sender = senderBook.getUser();

        receiverBook.setUser(sender);
        senderBook.setUser(receiver);
    }

    private void updateTradeRequestStatus(Trade trade, String status) {
        trade.setStatus(status);
        saveTradeRequest(trade);
    }

    private void saveBooks(List<Book> books) {
        bookRepository.saveAll(books);
    }

    private void cancelTradeRequest(Trade trade) {
        trade.setStatus(TradeStatus.Rejected.name());

        saveTradeRequest(trade);
    }

    @Override
    @Transactional
    public void processTradeRequest(TradeProcess tradeProcess) {
        if (tradeProcess.getResultStatus().equals(TradeStatus.Accepted.name())) {
            acceptTradeRequest(tradeRetrievalService.findTradeById(tradeProcess.getTradeRequestId()));
        } else {
            cancelTradeRequest(tradeRetrievalService.findTradeById(tradeProcess.getTradeRequestId()));
        }
    }
}
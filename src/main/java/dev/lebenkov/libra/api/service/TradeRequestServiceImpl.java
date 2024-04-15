package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.TradeRequestProcessDto;
import dev.lebenkov.libra.storage.dto.TradeRequestDto;
import dev.lebenkov.libra.storage.model.Book;
import dev.lebenkov.libra.storage.model.TradeHistory;
import dev.lebenkov.libra.storage.model.TradeRequest;
import dev.lebenkov.libra.storage.model.User;
import dev.lebenkov.libra.storage.repository.BookRepository;
import dev.lebenkov.libra.storage.repository.TradeHistoryRepository;
import dev.lebenkov.libra.storage.repository.TradeRequestRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradeRequestServiceImpl implements TradeRequestService {

    TradeRequestRepository tradeRequestRepository;

    BookRepository bookRepository; // TODO: change logic

    UserRetrievalService userRetrievalService;
    BookRetrievalService bookRetrievalService;
    TradeRetrievalService tradeRetrievalService;
    SessionUserProviderService sessionUserProviderService;
    TradeHistoryRepository tradeHistoryRepository;

    private TradeRequest createTradeRequestByTradeRequestDto(TradeRequestDto tradeRequestDto) {
        return TradeRequest.builder()
                .status("Pending")
                .tradeReceiver(userRetrievalService.findUserById((tradeRequestDto.getTradeReceiverId())))
                .tradeSender(sessionUserProviderService.getUserFromSession())
                .bookSender(bookRetrievalService.getBookById(tradeRequestDto.getBookSenderId()))
                .bookReceiver(bookRetrievalService.getBookById(tradeRequestDto.getBookReceiverId()))
                .build();
    }

    private TradeRequest saveTradeRequest(TradeRequest tradeRequest) {
        return tradeRequestRepository.save(tradeRequest);
    }

    private TradeHistory createTradeHistoryByTradeRequestDto(TradeRequestDto tradeRequestDto) {
        return TradeHistory.builder()
                .tradeDate(LocalDate.now())
                .tradeRequest(saveTradeRequest(createTradeRequestByTradeRequestDto(tradeRequestDto)))
                .build();
    }

    private void saveTradeHistory(TradeHistory tradeHistory) {
        tradeHistoryRepository.save(tradeHistory);
    }

    @Override
    public void sendTradeRequest(TradeRequestDto tradeRequestDto) {
        saveTradeHistory(createTradeHistoryByTradeRequestDto(tradeRequestDto));
    }

    private void saveBook(Book book) {
        bookRepository.save(book); // TODO: change logic
    }

    // TODO: change method logic
    private void acceptTradeRequest(TradeRequest tradeRequest) {
        Book receiverBook = tradeRequest.getBookReceiver();
        Book senderBook = tradeRequest.getBookSender();

        User sender = senderBook.getUser();
        User receiver = receiverBook.getUser();

        receiverBook.setUser(sender);
        senderBook.setUser(receiver);

        saveBook(receiverBook);
        saveBook(senderBook);

        tradeRequest.setStatus("Accepted");
        saveTradeRequest(tradeRequest);
    }

    private void cancelTradeRequest(TradeRequest tradeRequest) {
        tradeRequest.setStatus("Rejected");

        saveTradeRequest(tradeRequest);
    }

    @Override
    @Transactional
    public void processTradeRequest(TradeRequestProcessDto tradeRequestProcessDto) {
        if (tradeRequestProcessDto.getResultStatus().equals("Accepted")) {
            acceptTradeRequest(tradeRetrievalService.findTradeRequestById(tradeRequestProcessDto.getTradeRequestId()));
        } else {
            cancelTradeRequest(tradeRetrievalService.findTradeRequestById(tradeRequestProcessDto.getTradeRequestId()));
        }
    }
}
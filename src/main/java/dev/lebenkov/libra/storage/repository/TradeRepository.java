package dev.lebenkov.libra.storage.repository;

import dev.lebenkov.libra.storage.model.Trade;
import dev.lebenkov.libra.storage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findAllByTradeReceiverAndStatus(User tradeReceiver, String status);

    @Query("SELECT t FROM Trade t WHERE t.requestId = :requestId AND (t.tradeReceiver = :user OR t.tradeSender = :user)")
    Optional<Trade> findByRequestIdAndTradeReceiverOrTradeSender(Long requestId, User user);
}

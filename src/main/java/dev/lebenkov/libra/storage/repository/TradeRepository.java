package dev.lebenkov.libra.storage.repository;

import dev.lebenkov.libra.storage.model.Trade;
import dev.lebenkov.libra.storage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findAllByTradeReceiverAndStatus(User tradeReceiver, String status);
}

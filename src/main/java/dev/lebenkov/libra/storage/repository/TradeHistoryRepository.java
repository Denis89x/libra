package dev.lebenkov.libra.storage.repository;

import dev.lebenkov.libra.storage.model.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {
}

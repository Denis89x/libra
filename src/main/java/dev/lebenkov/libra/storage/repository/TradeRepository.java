package dev.lebenkov.libra.storage.repository;

import dev.lebenkov.libra.storage.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
}

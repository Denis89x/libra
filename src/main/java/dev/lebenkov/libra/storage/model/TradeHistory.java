package dev.lebenkov.libra.storage.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "trade_history")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradeHistory {

    @Id
    @Column(name = "trade_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long tradeId;

    @Column(name = "trade_date")
    LocalDate tradeDate;

    @ManyToOne
    @JoinColumn(name = "request_id")
    Trade trade;
}
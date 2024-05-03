package dev.lebenkov.libra.storage.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@Entity
@Table(name = "trade_request")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Trade {

    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long requestId;

    @Column(name = "status")
    String status;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    User tradeSender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    User tradeReceiver;

    @ManyToOne
    @JoinColumn(name = "sender_book_id")
    Book bookSender;

    @ManyToOne
    @JoinColumn(name = "receiver_book_id")
    Book bookReceiver;

    @ToString.Exclude
    @OneToMany(mappedBy = "trade", fetch = FetchType.LAZY)
    List<TradeHistory> tradeHistories = new ArrayList<>();
}
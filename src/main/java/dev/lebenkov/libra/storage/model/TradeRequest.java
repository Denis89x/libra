package dev.lebenkov.libra.storage.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "trade_request")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TradeRequest {

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

    @OneToMany(mappedBy = "tradeRequest", fetch = FetchType.LAZY)
    List<TradeHistory> tradeHistories = new ArrayList<>();
}
package dev.lebenkov.libra.storage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("book_id")
    Long bookId;

    @Column(name = "title")
    String title;

    @Column(name = "author")
    String author;

    @Column(name = "year_of_publication")
    @JsonProperty("year_of_publication")
    String yearOfPublication;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    Genre genre;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "bookSender", fetch = FetchType.LAZY)
    List<TradeRequest> sentBooks = new ArrayList<>();

    @OneToMany(mappedBy = "bookReceiver", fetch = FetchType.LAZY)
    List<TradeRequest> receivedBooks = new ArrayList<>();
}
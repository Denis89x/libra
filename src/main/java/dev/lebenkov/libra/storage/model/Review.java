package dev.lebenkov.libra.storage.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "review")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long reviewId;

    @Column(name = "rating")
    Double rating;

    @Column(name = "review_text")
    String reviewText;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User reviewer;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;
}

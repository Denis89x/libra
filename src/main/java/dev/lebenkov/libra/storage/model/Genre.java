package dev.lebenkov.libra.storage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genre")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Genre {

    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("genre_id")
    Long genreId;

    @Column(name = "title")
    String title;

    @OneToMany(mappedBy = "genre", fetch = FetchType.EAGER)
    List<Book> books = new ArrayList<>();
}
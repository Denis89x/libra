package dev.lebenkov.libra.storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {

    @JsonProperty("book_id")
    Long bookId;

    String title;

    String author;

    @JsonProperty("year_of_publication")
    String yearOfPublication;

    @JsonProperty("genre")
    GenreResponse genreResponse;
}
package dev.lebenkov.libra.storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {

    String title;

    String author;

    @JsonProperty("year_of_publication")
    String yearOfPublication;

    @JsonProperty("genre_id")
    Long genreId;
}
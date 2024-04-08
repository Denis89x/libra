package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.GenreRequest;
import dev.lebenkov.libra.storage.dto.GenreResponse;

import java.util.List;

public interface GenreCrudService {
    void createGenre(GenreRequest genreRequest);

    GenreResponse fetchGenreById(Long genreId);

    List<GenreResponse> fetchAllGenres();

    void updateGenre(Long genreId, GenreRequest genreRequest);

    void deleteGenreById(Long genreId);
}

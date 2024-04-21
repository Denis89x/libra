package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.util.exception.ObjectNotFoundException;
import dev.lebenkov.libra.storage.model.Genre;
import dev.lebenkov.libra.storage.repository.GenreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GenreRetrievalServiceImpl implements GenreRetrievalService {

    GenreRepository genreRepository;

    @Override
    public Genre fetchGenreEntityByGenreId(long genreId) {
        return genreRepository.findByGenreId(genreId)
                .orElseThrow(() -> new ObjectNotFoundException("Genre with " + genreId + " id not found!"));
    }
}

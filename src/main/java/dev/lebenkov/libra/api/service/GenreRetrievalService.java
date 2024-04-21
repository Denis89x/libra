package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.model.Genre;

public interface GenreRetrievalService {
    Genre fetchGenreEntityByGenreId(long genreId);
}

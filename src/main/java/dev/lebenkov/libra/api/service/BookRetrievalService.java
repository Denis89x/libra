package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.model.Book;

public interface BookRetrievalService {
    Book getBookOwnedByCurrentUserById(Long id);

    Book getBookOwnedByUserById(Long bookId, Long userId);

    Book getBookById(Long id);
}

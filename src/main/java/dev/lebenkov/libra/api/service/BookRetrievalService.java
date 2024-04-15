package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.model.Book;

public interface BookRetrievalService {
    Book getBookOwnedByCurrentUserById(Long id);

    Book getBookById(Long id);
}

package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.BookRequest;

public interface BookManagementService {
    void createBook(BookRequest bookRequest);
    void updateBook(Long bookId, BookRequest bookRequest);
    void deleteBookById(Long id);
}

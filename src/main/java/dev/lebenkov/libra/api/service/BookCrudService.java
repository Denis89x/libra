package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.BookRequest;
import dev.lebenkov.libra.storage.dto.BookResponse;

import java.util.List;

public interface BookCrudService {
    void createBook(BookRequest bookRequest);

    BookResponse fetchBookById(Long id);

    List<BookResponse> fetchAllBooks();

    void updateBook(Long bookId, BookRequest bookRequest);

    void deleteBookById(Long id);
}
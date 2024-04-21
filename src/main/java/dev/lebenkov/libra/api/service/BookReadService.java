package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.BookResponse;

import java.util.List;

public interface BookReadService {
    BookResponse fetchBookById(Long id);
    List<BookResponse> fetchAllBooks();
    List<BookResponse> fetchAllBooksByUserId(Long userId);
}

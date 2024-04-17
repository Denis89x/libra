package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.util.exception.ObjectNotFoundException;
import dev.lebenkov.libra.storage.model.Book;
import dev.lebenkov.libra.storage.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookRetrievalServiceImpl implements BookRetrievalService {

    BookRepository bookRepository;

    SessionUserProviderService sessionUserProviderService;

    @Override
    public Book getBookOwnedByCurrentUserById(Long id) {
        return bookRepository.findByBookIdAndUser_UserId(id, sessionUserProviderService.getUserFromSession().getUserId())
                .orElseThrow(() -> new ObjectNotFoundException("Book with " + id + " id not found!"));
    }

    @Override
    public Book getBookOwnedByUserById(Long bookId, Long userId) {
        return bookRepository.findByBookIdAndUser_UserId(bookId, userId)
                .orElseThrow(() -> new ObjectNotFoundException("Book with " + bookId + " id not found!"));
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Book with " + id + " not found!"));
    }
}
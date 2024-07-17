package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.util.exception.ObjectNotFoundException;
import dev.lebenkov.libra.storage.dto.BookRequest;
import dev.lebenkov.libra.storage.model.Book;
import dev.lebenkov.libra.storage.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookManagementServiceImpl implements BookManagementService {

    BookRepository bookRepository;

    BookRetrievalService bookRetrievalService;
    GenreRetrievalService genreRetrievalService;
    SessionUserProviderService sessionUserProviderService;

    private Book createBookEntity(BookRequest bookRequest) {
        return Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .yearOfPublication(bookRequest.getYearOfPublication())
                .genre(genreRetrievalService.fetchGenreEntityByGenreId(bookRequest.getGenreId()))
                .user(sessionUserProviderService.getUserFromSession())
                .build();
    }

    @Override
    public void createBook(BookRequest bookRequest) {
        Book book = createBookEntity(bookRequest);

        bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRetrievalService.getBookOwnedByCurrentUserById(id);

        bookRepository.deleteById(id);
    }

    private Book updateBookFromBookRequest(Book book, BookRequest bookRequest) {
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setYearOfPublication(bookRequest.getYearOfPublication());
        book.setGenre(genreRetrievalService.fetchGenreEntityByGenreId(bookRequest.getGenreId()));

        return book;
    }

    @Override
    public void updateBook(Long bookId, BookRequest bookRequest) {
        Book updatedBook = updateBookFromBookRequest(bookRetrievalService.getBookOwnedByCurrentUserById(bookId), bookRequest);

        bookRepository.save(updatedBook);
    }
}

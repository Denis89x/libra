package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.dto.BookResponse;
import dev.lebenkov.libra.storage.dto.GenreResponse;
import dev.lebenkov.libra.storage.model.Book;
import dev.lebenkov.libra.storage.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookReadServiceImpl implements BookReadService {

    ModelMapper modelMapper;
    BookRepository bookRepository;

    SessionUserProviderService sessionUserProviderService;
    BookRetrievalService bookRetrievalService;
    UserRetrievalService userRetrievalService;

    private BookResponse convertBookToBookResponse(Book book) {
        BookResponse bookResponse = modelMapper.map(book, BookResponse.class);

        bookResponse.setGenreResponse(modelMapper.map(book.getGenre(), GenreResponse.class));
        bookResponse.setBookOwner(userRetrievalService.findUserById(book.getUser().getUserId()).getUsername());

        return bookResponse;
    }

    @Override
    public BookResponse fetchBookById(Long id) {
        return convertBookToBookResponse(bookRetrievalService.getBookOwnedByCurrentUserById(id));
    }

    @Override
    public List<BookResponse> fetchAllBooks() {
        return bookRepository.findAllByUser(sessionUserProviderService.getUserFromSession()).stream()
                .map(this::convertBookToBookResponse)
                .toList();
    }

    @Override
    public List<BookResponse> fetchAllBooksByUserId(Long userId) {
        return bookRepository.findAllByUser(userRetrievalService.findUserById(userId)).stream()
                .map(this::convertBookToBookResponse)
                .toList();
    }
}

package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.util.exception.ObjectNotFoundException;
import dev.lebenkov.libra.storage.dto.BookRequest;
import dev.lebenkov.libra.storage.dto.BookResponse;
import dev.lebenkov.libra.storage.model.Book;
import dev.lebenkov.libra.storage.repository.BookRepository;
import dev.lebenkov.libra.storage.repository.GenreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookCrudServiceImpl implements BookCrudService {

    ModelMapper modelMapper;
    BookRepository bookRepository;
    GenreRepository genreRepository;

    SessionUserProviderService sessionUserProviderService;

    private Book createBookEntity(BookRequest bookRequest) {
        return Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .yearOfPublication(bookRequest.getYearOfPublication())
                .genre(genreRepository.findByGenreId(bookRequest.getGenreId())
                        .orElseThrow(() -> new ObjectNotFoundException("Genre with " + bookRequest.getGenreId() + " id not found!")))
                .user(sessionUserProviderService.getUserFromSession())
                .build();
    }

    @Override
    public void createBook(BookRequest bookRequest) {
        Book book = createBookEntity(bookRequest);

        bookRepository.save(book);
    }

    private Book getBookEntityById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Book with " + id + " id not found!"));
    }

    private BookResponse convertBookToBookResponse(Book book) {
        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public BookResponse fetchBookById(Long id) {
        return convertBookToBookResponse(getBookEntityById(id));
    }

    @Override
    public List<BookResponse> fetchAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertBookToBookResponse)
                .toList();
    }

    @Override
    public void updateBook(Long bookId, BookRequest bookRequest) {

    }

    @Override
    public void deleteBookById(Long id) {

    }
}
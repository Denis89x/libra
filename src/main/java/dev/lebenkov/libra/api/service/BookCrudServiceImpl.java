package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.util.exception.ObjectNotFoundException;
import dev.lebenkov.libra.storage.dto.BookRequest;
import dev.lebenkov.libra.storage.dto.BookResponse;
import dev.lebenkov.libra.storage.dto.GenreResponse;
import dev.lebenkov.libra.storage.model.Book;
import dev.lebenkov.libra.storage.model.Genre;
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

    private Genre fetchGenreEntityByGenreId(long genreId) {
        return genreRepository.findByGenreId(genreId)
                .orElseThrow(() -> new ObjectNotFoundException("Genre with " + genreId + " id not found!"));
    }

    private Book createBookEntity(BookRequest bookRequest) {
        return Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .yearOfPublication(bookRequest.getYearOfPublication())
                .genre(fetchGenreEntityByGenreId(bookRequest.getGenreId()))
                .user(sessionUserProviderService.getUserFromSession())
                .build();
    }

    @Override
    public void createBook(BookRequest bookRequest) {
        Book book = createBookEntity(bookRequest);

        bookRepository.save(book);
    }

    private Book getBookEntityById(long id) {
        return bookRepository.findByBookIdAndUser_UserId(id, sessionUserProviderService.getUserFromSession().getUserId())
                .orElseThrow(() -> new ObjectNotFoundException("Book with " + id + " id not found!"));
    }

    private BookResponse convertBookToBookResponse(Book book) {
        BookResponse bookResponse = modelMapper.map(book, BookResponse.class);

        bookResponse.setGenreResponse( modelMapper.map(book.getGenre(), GenreResponse.class));

        return bookResponse;
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

    private Book updateBookFromBookRequest(Book book, BookRequest bookRequest) {
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setYearOfPublication(bookRequest.getYearOfPublication());
        book.setGenre(fetchGenreEntityByGenreId(bookRequest.getGenreId()));

        return book;
    }

    @Override
    public void updateBook(Long bookId, BookRequest bookRequest) {
        Book updatedBook = updateBookFromBookRequest(getBookEntityById(bookId), bookRequest);

        bookRepository.save(updatedBook);
    }

    private void checkBookExistsById(long id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Book with " + id + " not found!"));
    }

    @Override
    public void deleteBookById(Long id) {
        checkBookExistsById(id);

        bookRepository.deleteById(id);
    }
}
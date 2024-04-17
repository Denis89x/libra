package dev.lebenkov.libra.api.controller;

import dev.lebenkov.libra.api.service.BookCrudService;
import dev.lebenkov.libra.storage.dto.BookRequest;
import dev.lebenkov.libra.storage.dto.BookResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController {

    BookCrudService bookCrudService;

    @GetMapping("/{book_id}")
    public ResponseEntity<BookResponse> fetchBookById(@PathVariable("book_id") Long bookId) {
        return new ResponseEntity<>(bookCrudService.fetchBookById(bookId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> fetchAllBooks() {
        return new ResponseEntity<>(bookCrudService.fetchAllBooks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody BookRequest bookRequest) {
        bookCrudService.createBook(bookRequest);
        return ResponseEntity.ok("Book was successfully added!");
    }

    @PutMapping("/{book_id}")
    public ResponseEntity<String> updateBook(
            @PathVariable("book_id") Long bookId,
            @RequestBody BookRequest bookRequest) {
        bookCrudService.updateBook(bookId, bookRequest);
        return ResponseEntity.ok("Book was successfully updated!");
    }

    @DeleteMapping("/{book_id}")
    public ResponseEntity<String> deleteBook(@PathVariable("book_id") Long bookId) {
        bookCrudService.deleteBookById(bookId);
        return ResponseEntity.ok("Book was successfully deleted!");
    }
}
package dev.lebenkov.libra.api.controller;

import dev.lebenkov.libra.api.service.BookManagementService;
import dev.lebenkov.libra.api.service.BookReadService;
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

    BookReadService bookReadService;
    BookManagementService bookManagementService;

    @GetMapping("/{book_id}")
    public ResponseEntity<BookResponse> fetchBookById(@PathVariable("book_id") Long bookId) {
        return new ResponseEntity<>(bookReadService.fetchBookById(bookId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> fetchAllBooks() {
        return new ResponseEntity<>(bookReadService.fetchAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/get-all-users-book/{user_id}")
    public ResponseEntity<List<BookResponse>> fetchAllBooksByUserId(@PathVariable("user_id") Long userId) {
        return new ResponseEntity<>(bookReadService.fetchAllBooksByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody BookRequest bookRequest) {
        bookManagementService.createBook(bookRequest);
        return ResponseEntity.ok("Book was successfully added!");
    }

    @PutMapping("/{book_id}")
    public ResponseEntity<String> updateBook(
            @PathVariable("book_id") Long bookId,
            @RequestBody BookRequest bookRequest) {
        bookManagementService.updateBook(bookId, bookRequest);
        return ResponseEntity.ok("Book was successfully updated!");
    }

    @DeleteMapping("/{book_id}")
    public ResponseEntity<String> deleteBook(@PathVariable("book_id") Long bookId) {
        bookManagementService.deleteBookById(bookId);
        return ResponseEntity.ok("Book was successfully deleted!");
    }
}
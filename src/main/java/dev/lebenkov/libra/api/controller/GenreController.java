package dev.lebenkov.libra.api.controller;

import dev.lebenkov.libra.api.service.GenreCrudService;
import dev.lebenkov.libra.storage.dto.GenreRequest;
import dev.lebenkov.libra.storage.dto.GenreResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genres")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GenreController {

    GenreCrudService genreCrudService;

    @GetMapping("/{genre_id}")
    public ResponseEntity<GenreResponse> fetchGenreById(@PathVariable("genre_id") Long genreId) {
        return new ResponseEntity<>(genreCrudService.fetchGenreById(genreId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GenreResponse>> fetchAllGenres() {
        return new ResponseEntity<>(genreCrudService.fetchAllGenres(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createGenre(@RequestBody GenreRequest genreRequest) {
        genreCrudService.createGenre(genreRequest);
        return new ResponseEntity<>("Genre was successfully created", HttpStatus.CREATED);
    }

    @PutMapping("/{genre_id}")
    public ResponseEntity<String> updateGenre(
            @PathVariable("genre_id") Long genreId,
            @RequestBody GenreRequest genreRequest) {
        genreCrudService.updateGenre(genreId, genreRequest);
        return ResponseEntity.ok("Genre was successfully updated!");
    }

    @DeleteMapping("/{genre_id}")
    public ResponseEntity<String> deleteGenre(@PathVariable("genre_id") Long genreId) {
        genreCrudService.deleteGenreById(genreId);
        return ResponseEntity.ok("Genre was successfully deleted!");
    }
}
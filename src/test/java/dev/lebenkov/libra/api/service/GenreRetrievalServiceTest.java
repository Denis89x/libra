package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.storage.model.Genre;
import dev.lebenkov.libra.storage.repository.GenreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class GenreRetrievalServiceTest {

    @Mock
    private GenreRepository mockGenreRepository;

    GenreRetrievalService genreRetrievalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        genreRetrievalService = new GenreRetrievalServiceImpl(mockGenreRepository);
    }

    @Test
    void GenreRetrievalService_FetchGenreEntityByGenreId_ReturnsGenre() {
        // Arrange
        long genreId = 1;

        Genre genre = Genre.builder()
                .genreId(genreId)
                .title("testTitle")
                .build();

        Mockito.when(mockGenreRepository.findByGenreId(Mockito.anyLong())).thenReturn(Optional.of(genre));

        // Act
        Genre foundGenre = genreRetrievalService.fetchGenreEntityByGenreId(genreId);

        // Assert
        Assertions.assertThat(foundGenre).isNotNull();
        Assertions.assertThat(foundGenre.getGenreId()).isEqualTo(genreId);
        Mockito.verify(mockGenreRepository, Mockito.times(1)).findByGenreId(Mockito.anyLong());
    }
}
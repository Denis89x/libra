package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.util.exception.ObjectNotFoundException;
import dev.lebenkov.libra.storage.dto.GenreRequest;
import dev.lebenkov.libra.storage.dto.GenreResponse;
import dev.lebenkov.libra.storage.model.Genre;
import dev.lebenkov.libra.storage.repository.GenreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GenreCrudServiceTest {

    private final ModelMapper modelMapper = new ModelMapper();

    @Mock
    private GenreRepository mockGenreRepository;

    private GenreCrudService genreCrudService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        genreCrudService = new GenreCrudServiceImpl(modelMapper, mockGenreRepository);
    }

    @Test
    void GenreCrudService_CreateGenre_ReturnVoid() {
        // Arrange
        GenreRequest genreRequest = GenreRequest.builder()
                .title("testTitle")
                .build();

        Genre genre = Genre.builder()
                .title("testTitle")
                .build();

        Mockito.when(mockGenreRepository.save(Mockito.any(Genre.class))).thenReturn(genre);

        // Act
        genreCrudService.createGenre(genreRequest);

        // Assert
        Mockito.verify(mockGenreRepository, Mockito.times(1)).save(Mockito.any(Genre.class));
    }

    @Test
    public void GenreCrudService_FetchGenreById_ReturnsGenreResponse() {
        // Arrange
        long genreId = 1;

        Genre genre = Genre.builder()
                .genreId(genreId)
                .title("testTitle")
                .build();

        Mockito.when(mockGenreRepository.findByGenreId(Mockito.anyLong())).thenReturn(Optional.of(genre));

        // Act
        GenreResponse genreResponse = genreCrudService.fetchGenreById(genreId);

        // Assert
        Assertions.assertThat(genreResponse).isNotNull();
    }

    @Test
    public void GenreCrudService_FetchAllGenres_ReturnsGenreResponses() {
        // Arrange
        Genre genre1 = Genre.builder()
                .title("testTitle")
                .build();

        Genre genre2 = Genre.builder()
                .title("testTitle")
                .build();

        Mockito.when(mockGenreRepository.findAll()).thenReturn(List.of(genre1, genre2));

        // Act
        List<GenreResponse> genreResponses = genreCrudService.fetchAllGenres();

        // Assert
        Assertions.assertThat(genreResponses).isNotNull();
        Assertions.assertThat(genreResponses.size()).isEqualTo(2);
        Assertions.assertThat(genreResponses.get(0).getTitle()).isEqualTo(genre1.getTitle());
    }

    @Test
    public void GenreCrudService_UpdateGenre_ReturnsVoid() {
        // Arrange
        long genreId = 1;

        GenreRequest genreRequest = GenreRequest.builder()
                .title("testTitle")
                .build();

        Genre genre = Genre.builder()
                .genreId(genreId)
                .title("testTitle")
                .build();

        Mockito.when(mockGenreRepository.findByGenreId(Mockito.anyLong())).thenReturn(Optional.of(genre));
        Mockito.when(mockGenreRepository.save(Mockito.any(Genre.class))).thenReturn(genre);

        // Act
        genreCrudService.updateGenre(genreId, genreRequest);

        // Assert
        Mockito.verify(mockGenreRepository, Mockito.times(1)).save(Mockito.any(Genre.class));
    }

    @Test
    public void GenreCrudService_DeleteGenreById_ReturnsObjectNotFoundException() {
        // Arrange
        long genreId = 1;

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> genreCrudService.deleteGenreById(genreId));
    }

    @Test
    public void GenreCrudService_DeleteGenreById_ReturnsVoid() {
        // Arrange
        long genreId = 1;

        Genre genre = Genre.builder()
                .genreId(genreId)
                .title("testTitle")
                .build();

        Mockito.when(mockGenreRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(genre));

        // Act
        genreCrudService.deleteGenreById(genreId);

        // Assert
        Mockito.verify(mockGenreRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(mockGenreRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}
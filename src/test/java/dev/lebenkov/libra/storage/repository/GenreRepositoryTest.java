package dev.lebenkov.libra.storage.repository;

import dev.lebenkov.libra.storage.model.Genre;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(properties = {"spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop", "spring.jpa.show-sql=true"})
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void GenreRepository_FindByGenreId_ReturnGenre() {
        // Arrange
        Genre genre = Genre.builder()
                .title("title")
                .build();

        // Act
        genreRepository.save(genre);

        Genre foundGenre = genreRepository.findByGenreId(genre.getGenreId()).get();

        // Assert
        Assertions.assertThat(foundGenre).isNotNull();
        Assertions.assertThat(foundGenre.getTitle()).isEqualTo(genre.getTitle());
    }
}
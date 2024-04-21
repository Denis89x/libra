package dev.lebenkov.libra.storage.repository;

import dev.lebenkov.libra.storage.model.Book;
import dev.lebenkov.libra.storage.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(properties = {"spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop", "spring.jpa.show-sql=true"})
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void BookRepository_FindAllByUser_ReturnsAllBooks() {
        // Arrange
        User user1 = User.builder()
                .username("username")
                .password("password")
                .email("email@mail.ru")
                .registrationDate(LocalDate.now())
                .build();

        User user2 = User.builder()
                .username("username")
                .password("password")
                .email("email@mail.ru")
                .registrationDate(LocalDate.now())
                .build();

        Book book1 = Book.builder()
                .title("title1")
                .author("author1")
                .user(user1)
                .build();

        Book book2 = Book.builder()
                .title("title2")
                .author("author2")
                .user(user2)
                .build();

        // Act
        userRepository.save(user1);
        userRepository.save(user2);

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findAllByUser(user1);

        // Assert
        Assertions.assertThat(books).isNotNull();
        Assertions.assertThat(books.size()).isEqualTo(1);
    }

    @Test
    void BookRepository_FindByBookIdAndUser_UserId_ReturnsBook() {
        // Arrange
        User user1 = User.builder()
                .username("username")
                .password("password")
                .email("email@mail.ru")
                .registrationDate(LocalDate.now())
                .build();

        Book book1 = Book.builder()
                .title("title1")
                .author("author1")
                .user(user1)
                .build();

        // Act
        userRepository.save(user1);
        bookRepository.save(book1);

        Book foundBook = bookRepository.findByBookIdAndUser_UserId(book1.getBookId(), user1.getUserId()).get();

        // Assert
        Assertions.assertThat(foundBook).isNotNull();
        Assertions.assertThat(foundBook.getTitle()).isEqualTo(book1.getTitle());
    }
}
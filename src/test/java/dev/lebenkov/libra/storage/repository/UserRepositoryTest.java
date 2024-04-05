package dev.lebenkov.libra.storage.repository;

import dev.lebenkov.libra.storage.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(properties = {"spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop", "spring.jpa.show-sql=true"})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_FindByUsername_ReturnUser() {
        // Arrange
        User user = User.builder()
                .username("username")
                .password("password")
                .email("email@mail.ru")
                .registrationDate(LocalDate.now())
                .build();

        // Act
        userRepository.save(user);

        User userFind = userRepository.findByUsername("username").get();

        // Assert
        Assertions.assertThat(userFind).isNotNull();
        Assertions.assertThat(userFind.getUserId()).isGreaterThan(0);
    }
}

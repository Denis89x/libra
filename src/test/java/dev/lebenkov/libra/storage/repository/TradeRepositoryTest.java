package dev.lebenkov.libra.storage.repository;

import dev.lebenkov.libra.storage.enums.TradeStatus;
import dev.lebenkov.libra.storage.model.Book;
import dev.lebenkov.libra.storage.model.Trade;
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
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(properties = {"spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop", "spring.jpa.show-sql=true"})
class TradeRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void TradeRepository_FindAllByTradeReceiverAndStatus_ReturnsTrades() {
        // Arrange
        User user1 = User.builder()
                .username("username1")
                .password("password")
                .email("email@mail.ru")
                .registrationDate(LocalDate.now())
                .build();

        User user2 = User.builder()
                .username("username2")
                .password("password")
                .email("email@mail.ru")
                .registrationDate(LocalDate.now())
                .build();

        Book book1 = Book.builder()
                .title("title1")
                .author("author2")
                .user(user1)
                .build();

        Book book2 = Book.builder()
                .title("title2")
                .author("author2")
                .user(user2)
                .build();

        Trade trade1 = Trade.builder()
                .status(TradeStatus.Pending.name())
                .tradeSender(user1)
                .tradeReceiver(user2)
                .bookSender(book1)
                .bookReceiver(book2)
                .build();

        // Act
        bookRepository.save(book1);
        bookRepository.save(book2);

        userRepository.save(user1);
        userRepository.save(user2);

        tradeRepository.save(trade1);

        List<Trade> foundTrades = tradeRepository.findAllByTradeReceiverAndStatus(user2, TradeStatus.Pending.name());

        // Assert
        Assertions.assertThat(foundTrades).isNotNull();
        Assertions.assertThat(foundTrades.size()).isEqualTo(1);
        Assertions.assertThat(foundTrades.getFirst().getStatus()).isEqualTo(TradeStatus.Pending.name());
    }

    @Test
    void TradeRepository_FindByRequestIdAndTradeReceiverOrTradeSender_ReturnsTrade() {
        // Arrange
        User user1 = User.builder()
                .username("username1")
                .password("password")
                .email("email@mail.ru")
                .registrationDate(LocalDate.now())
                .build();

        User user2 = User.builder()
                .username("username2")
                .password("password")
                .email("email@mail.ru")
                .registrationDate(LocalDate.now())
                .build();

        User user3 = User.builder()
                .username("username3")
                .password("password")
                .email("email@mail.ru")
                .registrationDate(LocalDate.now())
                .build();

        Book book1 = Book.builder()
                .title("title1")
                .author("author2")
                .user(user1)
                .build();

        Book book2 = Book.builder()
                .title("title2")
                .author("author2")
                .user(user2)
                .build();

        Trade trade1 = Trade.builder()
                .status(TradeStatus.Pending.name())
                .tradeSender(user1)
                .tradeReceiver(user2)
                .bookSender(book1)
                .bookReceiver(book2)
                .build();

        // Act
        bookRepository.save(book1);
        bookRepository.save(book2);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        tradeRepository.save(trade1);

        Optional<Trade> foundTrade1 = tradeRepository.findByRequestIdAndTradeReceiverOrTradeSender(trade1.getRequestId(), user1);
        Optional<Trade> foundTrade2 = tradeRepository.findByRequestIdAndTradeReceiverOrTradeSender(trade1.getRequestId(), user2);
        Optional<Trade> emptyTrade = tradeRepository.findByRequestIdAndTradeReceiverOrTradeSender(trade1.getRequestId(), user3);

        // Assert
        Assertions.assertThat(foundTrade1).isNotNull();
        Assertions.assertThat(foundTrade2).isNotNull();
        Assertions.assertThat(emptyTrade).isEqualTo(Optional.empty());
        Assertions.assertThat(foundTrade1.get().getStatus()).isEqualTo(TradeStatus.Pending.name());
        Assertions.assertThat(foundTrade2.get().getStatus()).isEqualTo(TradeStatus.Pending.name());
    }
}
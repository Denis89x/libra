package dev.lebenkov.libra.storage.repository;

import dev.lebenkov.libra.storage.model.Book;
import dev.lebenkov.libra.storage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByBookIdAndUser_UserId(Long bookId, Long userId);

    List<Book> findAllByUser(User user);
}
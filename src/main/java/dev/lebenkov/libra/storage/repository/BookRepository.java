package dev.lebenkov.libra.storage.repository;

import dev.lebenkov.libra.storage.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}

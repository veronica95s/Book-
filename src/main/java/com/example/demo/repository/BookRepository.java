package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTituloContainingIgnoreCase(String titulo);

    List<Book> findByAutor_NomeContainingIgnoreCase(String nome);

    List<Book> findByTituloContainingIgnoreCaseAndAutor_NomeContainingIgnoreCase(
            String titulo,
            String nomeAutor
    );
}

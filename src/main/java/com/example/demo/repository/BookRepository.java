package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Buscar livros por título (contém, ignorando maiúsc/minúsc)
    List<Book> findByTituloContainingIgnoreCase(String titulo);

    // Buscar livros por nome do autor (contém, ignorando maiúsc/minúsc)
    List<Book> findByAutor_NomeContainingIgnoreCase(String nome);

    // Buscar por título + autor (ambos opcionais)
    List<Book> findByTituloContainingIgnoreCaseAndAutor_NomeContainingIgnoreCase(
            String titulo,
            String nomeAutor
    );
}

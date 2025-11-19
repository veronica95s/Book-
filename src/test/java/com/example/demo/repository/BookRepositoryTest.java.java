package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Book;
import com.example.demo.model.Person;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("test")

class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void SalvarECarregarLivro() {
        Person autor = personRepository.save(new Person("George Orwell"));
        Book livro = new Book("1984", autor);

        Book salvo = bookRepository.save(livro);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.gettitulo()).isEqualTo("1984");
        assertThat(salvo.getautor().getnome()).isEqualTo("George Orwell");
    }

    @Test
    void RetornarTodosOsLivros() {
        Person autor = personRepository.save(new Person("Aldous Huxley"));
        bookRepository.save(new Book("Admirável Mundo Novo", autor));

        List<Book> livros = bookRepository.findAll();

        assertThat(livros).isNotEmpty();
    }

    @Test
    void EncontrarLivroPorId() {
        Person autor = personRepository.save(new Person("Ray Bradbury"));
        Book livro = bookRepository.save(new Book("Fahrenheit 451", autor));

        Book encontrado = bookRepository.findById(livro.getId()).orElse(null);

        assertThat(encontrado).isNotNull();
        assertThat(encontrado.gettitulo()).isEqualTo("Fahrenheit 451");
    }

    @Test
void EncontrarPorTitulo() {
    Person autor = personRepository.save(new Person("Anthony Burgess"));
    bookRepository.save(new Book("Laranja Mecânica", autor));

    List<Book> encontrados = bookRepository.findByTituloContainingIgnoreCase("Laranja Mecânica");


    assertThat(encontrados).isNotEmpty();
    assertThat(encontrados.get(0).gettitulo()).isEqualTo("Laranja Mecânica");
}

@Test
void EncontraPorNomeDoAutor() {
    Person autor = personRepository.save(new Person("George Orwell"));
    bookRepository.save(new Book("A Revolução dos Bichos", autor));
    bookRepository.save(new Book("1984", autor));

    List<Book> encontrados =
        bookRepository.findByAutor_NomeContainingIgnoreCase("Orwell");

        assertThat(encontrados).hasSize(2);

}

 @Test
    void DeletarLivro() {
        Person autor = personRepository.save(new Person("Autor X"));
        Book livro = bookRepository.save(new Book("Livro X", autor));

        bookRepository.delete(livro);

        assertThat(bookRepository.findById(livro.getId())).isEmpty();
    }
}
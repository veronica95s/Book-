package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Book;
import com.example.demo.model.Person;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PersonRepository;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    public BookController(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

   
    @GetMapping
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return bookRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

   
    @GetMapping("/search")
    public List<Book> search(
        @RequestParam(required = false) String titulo,
        @RequestParam(required = false) String autor) 
    {
        return bookRepository.findByTituloContainingIgnoreCaseAndAutor_NomeContainingIgnoreCase(
            titulo != null ? titulo : "",
            autor != null ? autor : ""
        );
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody Book book) {
        if (book.getautor() == null || book.getautor().getId() == null)
            return ResponseEntity.badRequest().body("Autor inválido.");

        Optional<Person> autor = personRepository.findById(book.getautor().getId());
        if (autor.isEmpty())
            return ResponseEntity.badRequest().body("Autor não existe.");

        book.setautor(autor.get());
        Book saved = bookRepository.save(book);
        return ResponseEntity.ok(saved);
    }

 
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book) {
        return bookRepository.findById(id).map(existing -> {

            existing.settitulo(book.gettitulo());

            if (book.getautor() != null && book.getautor().getId() != null) {
                Person autor = personRepository.findById(book.getautor().getId())
                                .orElse(null);
                if (autor == null)
                    return ResponseEntity.badRequest().body("Autor não encontrado.");
                existing.setautor(autor);
            }

            return ResponseEntity.ok(bookRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return bookRepository.findById(id).map(livro -> {
            bookRepository.delete(livro);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

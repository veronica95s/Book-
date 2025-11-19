package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.BookController;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.model.Book;
import com.example.demo.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;



@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private PersonRepository personRepository;

    private final ObjectMapper mapper = new ObjectMapper();

 
    @Test
    void RetornarListaDeLivros() throws Exception {
        Person autor = new Person("Orwell");
        Book book = new Book("1984", autor);

        when(bookRepository.findAll()).thenReturn(List.of(book));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("1984"))
                .andExpect(jsonPath("$[0].autor.nome").value("Orwell"));
    }

 
    @Test
    void AdicionarLivro() throws Exception {
        Person autor = new Person("Orwell");
        autor.setId(1L);

        Book livro = new Book("1984", autor);

        when(personRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(bookRepository.save(any(Book.class))).thenReturn(livro);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(livro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("1984"))
                .andExpect(jsonPath("$.autor.nome").value("Orwell"));
    }


    

    @Test
    void EditarLivro() throws Exception {
        Person autorOriginal = new Person("Autor Antigo");
        autorOriginal.setId(1L);

        Person autorNovo = new Person("Autor Novo");
        autorNovo.setId(2L);

        Book existente = new Book("Titulo Antigo", autorOriginal);
        existente.setId(10L);

        Book atualizado = new Book("Titulo Atualizado", autorNovo);

        when(bookRepository.findById(10L)).thenReturn(Optional.of(existente));
        when(personRepository.findById(2L)).thenReturn(Optional.of(autorNovo));
        when(bookRepository.save(any(Book.class))).thenReturn(existente);

        mockMvc.perform(put("/books/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Titulo Atualizado"))
                .andExpect(jsonPath("$.autor.nome").value("Autor Novo"));
    }

    @Test
    void EditarLivroNaoEncontrado() throws Exception {
        when(bookRepository.findById(50L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/books/50")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\": \"teste\"}"))
                .andExpect(status().isNotFound());
    }

    
    @Test
    void DeletarLivro() throws Exception {
        Person autor = new Person("X");
        Book livro = new Book("Livro", autor);
        livro.setId(5L);

        when(bookRepository.findById(5L)).thenReturn(Optional.of(livro));
        doNothing().when(bookRepository).delete(livro);

        mockMvc.perform(delete("/books/5"))
                .andExpect(status().isOk());
    }


}

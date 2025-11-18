package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;


@RestController
public class Controller {
    
    private final BookRepository bookRepository;

    public Controller(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}

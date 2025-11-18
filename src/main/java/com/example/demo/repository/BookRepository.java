package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.Book;


public interface BookRepository extends JpaRepository
<Book, Long> {
}

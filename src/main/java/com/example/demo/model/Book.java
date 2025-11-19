package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    @ManyToOne
    private Person autor;

    public Book() { }

    public Book(String titulo, Person autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

     public void setId(Long id) {
        this.id = id;
    }

    public String gettitulo() {
        return titulo;
    }

    public Person getautor() {
        return autor;
    }

    public void settitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setautor(Person autor) {
        this.autor = autor;
    }
}

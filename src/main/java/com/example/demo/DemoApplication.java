package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.Book;
import com.example.demo.model.Person;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PersonRepository;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepo, PersonRepository personRepo) {
        return args -> {

            Person huxley = personRepo.save(new Person("Aldous Huxley"));
            Person bradbury = personRepo.save(new Person("Ray Bradbury"));
            Person burgess = personRepo.save(new Person("Anthony Burgess"));
            Person orwell = personRepo.save(new Person("George Orwell"));
            Person gibson = personRepo.save(new Person("William Gibson"));
            Person asimov = personRepo.save(new Person("Isaac Asimov"));
            Person zamyatin = personRepo.save(new Person("Yevgeny Zamyatin"));
            Person kafka = personRepo.save(new Person("Franz Kafka"));
            Person tolkien = personRepo.save(new Person("J.R.R. Tolkien"));

            bookRepo.save(new Book("Admirável Mundo Novo", huxley));
            bookRepo.save(new Book("Fahrenheit 451", bradbury));
            bookRepo.save(new Book("Laranja Mecânica", burgess));
            bookRepo.save(new Book("1984", orwell));
            bookRepo.save(new Book("Neuromancer", gibson));
            bookRepo.save(new Book("A Revolução dos Bichos", orwell));
            bookRepo.save(new Book("Eu, Robô", asimov));
            bookRepo.save(new Book("Nós", zamyatin));
            bookRepo.save(new Book("A Metamorfose", kafka));
            bookRepo.save(new Book("O Senhor dos Anéis", tolkien));
        };
    }
}

package com.example.bookstore;

import com.example.bookstore.model.Author;
import com.example.bookstore.repository.AuthorRepository;
import liquibase.pro.packaged.A;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author saveAuthor(Author author) {
        if (author.getBirthDate().isAfter(LocalDate.now()) || author.getBirthDate().isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date must be in the past");
        }
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

}

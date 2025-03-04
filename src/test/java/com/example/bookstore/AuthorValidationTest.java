package com.example.bookstore;

import com.example.bookstore.model.Author;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthorValidationTest {

    @Test
    void shouldNotAllowFutureBirthDate(){
        Author author = new Author();
        author.setBirthDate(LocalDate.now().plusDays(1));//Future date
        assertThrows(Exception.class, () -> validateAuthor(author));
    }

    private void validateAuthor(Author author) {
        if (author.getBirthDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date must be in the past");
        }
    }
}

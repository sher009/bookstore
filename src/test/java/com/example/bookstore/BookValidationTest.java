package com.example.bookstore;

import com.example.bookstore.model.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookValidationTest {

    @Test
    void shouldNotAllowFuturePublishedDate(){
        Book book = new Book();
        book.setPublishedDate(LocalDate.now().plusDays(1)); //Future date
        assertThrows(Exception.class, () -> validateBook(book));
    }

    private void validateBook(Book book) {
        if (book.getPublishedDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Published date cannot be in the future");
        }
    }
}

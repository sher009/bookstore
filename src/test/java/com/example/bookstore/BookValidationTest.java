package com.example.bookstore;

import com.example.bookstore.model.Book;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class BookValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldNotAllowFuturePublishedDate() {
        Book book = new Book();
        book.setPublishedDate(LocalDate.now().plusDays(1)); // Future date

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertFalse(violations.isEmpty(), "Validation should fail for future dates");
    }
}

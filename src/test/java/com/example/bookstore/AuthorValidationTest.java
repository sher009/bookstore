package com.example.bookstore;

import com.example.bookstore.model.Author;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class AuthorValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldNotAllowFutureBirthDate(){
        Author author = new Author();
        author.setBirthDate(LocalDate.now().plusDays(1)); // Future date

        Set<ConstraintViolation<Author>> violations = validator.validate(author);

        assertFalse(violations.isEmpty(), "Validation should fail for future birth dates");
    }
}

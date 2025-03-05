package com.example.bookstore;

import com.example.bookstore.validation.PastOrPresentValidator;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validator;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PastOrPresentValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldNoAllowFutureDate(){
        LocalDate futureDate = LocalDate.now().plusDays(1);
        PastOrPresentValidator validator = new PastOrPresentValidator();

        //Validate the future date (should fail)
        assertFalse(validator.isValid(futureDate, null), "Date should not be in the future");
    }

    @Test
    void shouldAllowPastDate(){
        LocalDate pastDate = LocalDate.now().minusDays(1);
        PastOrPresentValidator validator = new PastOrPresentValidator();

        //Validate the past date (should pass)
        assertTrue(validator.isValid(pastDate, null), "Date should not be in the past");
    }

    @Test
    void shouldAllowPresentDate(){
        LocalDate presentDate = LocalDate.now();
        PastOrPresentValidator validator = new PastOrPresentValidator();

        //Validate the present date (should pass)
        assertTrue(validator.isValid(presentDate, null), "Date should not be in the present");
    }

    @Test
    void shouldAllowNullDate(){
        PastOrPresentValidator validator = new PastOrPresentValidator();

        //Validate null value (should pass)
        assertTrue(validator.isValid(null, null), "Null Date should not be in the past");
    }

}

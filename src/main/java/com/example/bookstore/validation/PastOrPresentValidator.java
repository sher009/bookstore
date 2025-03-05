package com.example.bookstore.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class PastOrPresentValidator implements ConstraintValidator<PastOrPresent, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        // If value is null, we allow it (you can change this behavior if you want)
        if (value == null) {
            return true;
        }
        return !value.isAfter(LocalDate.now()); // Validate if date is not in the future
    }
}

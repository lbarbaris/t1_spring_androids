package org.example.syntheticcorestarter.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;

public class DateTimeValidator implements ConstraintValidator<DateTime, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;
        try {
            LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}


package org.example.syntheticcorestarter.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = DateTimeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTime {
    String message() default "Invalid date-time format. Expected ISO 8601 format.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


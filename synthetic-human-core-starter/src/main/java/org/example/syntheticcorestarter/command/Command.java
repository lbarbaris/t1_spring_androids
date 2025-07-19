package org.example.syntheticcorestarter.command;

import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.syntheticcorestarter.validation.DateTime;

@Data
public class Command {

    public Command(String time, Priority priority, String author, String description) {
        this.time = time;
        this.priority = priority;
        this.author = author;
        this.description = description;
        validate();
    }

    @DateTime
    @NotNull
    private String time;

    @NotNull
    private Priority priority;

    @Size(max = 100)
    private String author;

    @Size(max = 1000)
    private String description;

    private void validate() {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var violations = validator.validate(this);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .reduce("", (s1, s2) -> s1 + "\n" + s2));
        }
    }
}

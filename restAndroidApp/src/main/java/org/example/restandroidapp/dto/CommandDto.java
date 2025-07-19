package org.example.restandroidapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.syntheticcorestarter.command.Priority;
import org.example.syntheticcorestarter.validation.DateTime;

import java.time.Instant;
import java.util.Map;

public record CommandDto(
         String time,
         Priority priority,
         String author,
         String description
) {}

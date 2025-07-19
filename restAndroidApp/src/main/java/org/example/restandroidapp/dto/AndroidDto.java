package org.example.restandroidapp.dto;

import java.util.Map;

public record AndroidDto(Long id, Map<String, Integer> completedTasksByAuthor) {}


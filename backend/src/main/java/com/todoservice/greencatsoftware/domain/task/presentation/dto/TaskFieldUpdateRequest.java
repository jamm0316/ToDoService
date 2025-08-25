package com.todoservice.greencatsoftware.domain.task.presentation.dto;

public record TaskFieldUpdateRequest(
        String fieldType,
        Object value
) {
}

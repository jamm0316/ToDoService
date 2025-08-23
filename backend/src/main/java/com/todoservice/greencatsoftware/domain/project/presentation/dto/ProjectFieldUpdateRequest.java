package com.todoservice.greencatsoftware.domain.project.presentation.dto;

public record ProjectFieldUpdateRequest (
        String fieldType,
        Object value
) {
}

package com.todoservice.greencatsoftware.domain.project.presentation.dto;

import com.todoservice.greencatsoftware.common.enums.Visibility;

import java.time.LocalDate;

public record ProjectSummaryResponse (
        Long id,
        Long colorId,
        String name,
        Visibility visibility,
        LocalDate startDate,
        LocalDate endDate,
        double progress
) {

}

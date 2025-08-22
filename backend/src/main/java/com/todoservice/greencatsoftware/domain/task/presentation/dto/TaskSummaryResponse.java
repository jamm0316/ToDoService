package com.todoservice.greencatsoftware.domain.task.presentation.dto;

import com.todoservice.greencatsoftware.common.enums.DayLabel;
import com.todoservice.greencatsoftware.common.enums.Priority;
import com.todoservice.greencatsoftware.common.enums.Status;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskSummaryResponse (
        @NotNull(message = "제목은 필수입니다.")
        String title,

        @NotNull(message = "상태는 필수 입니다.")
        Status status,

        @NotNull(message = "priority는 필수 입니다.")
        Priority priority,

        LocalDate dueDate,

        DayLabel dayLabel,

        @NotNull(message = "colorId는 필수 입니다.")
        Long colorId  //project.id와 동일한 색상
) {
}

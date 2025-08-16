package com.todoservice.greencatsoftware.domain.task.dto;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.enums.DayLabel;
import com.todoservice.greencatsoftware.common.enums.Priority;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record TaskCreateRequest(
        @NotNull(message = "projectId는 필수 입니다.")
        Long projectId,

        @NotNull(message = "colorId는 필수 입니다.")
        Long colorId,

        @NotNull(message = "priority는 필수 입니다.")
        Priority priority,

        @NotNull(message = "제목은 필수입니다.")
        String title,

        String description,
        DayLabel dayLabel,
        LocalDate startDate,
        LocalDateTime startTime,

        @NotNull(message = "시작 시간 사용 여부는 필수입니다.")
        Boolean startTimeEnabled,

        LocalDate dueDate,
        LocalDateTime dueTime,

        @NotNull(message = "마감 시간 사용 여부는 필수입니다.")
        Boolean dueTimeEnabled,

        @NotNull(message = "상태는 필수 입니다.")
        Status status
) {
        public TaskCreateRequest {
                // description이 빈 문자열이면 null로 정규화
                if (description != null && description.isBlank()) description = null;

                // 날짜 일관성 검증
                if (startDate != null && dueDate != null && dueDate.isBefore(startDate)) {
                        throw new BaseException(BaseResponseStatus.END_DATE_BEFORE_START_DATE);
                }
        }
}

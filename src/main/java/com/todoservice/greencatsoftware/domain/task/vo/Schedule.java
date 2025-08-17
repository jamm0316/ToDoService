package com.todoservice.greencatsoftware.domain.task.vo;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
public record Schedule(
        LocalDate startDate,
        LocalDateTime startTime,
        @Column(columnDefinition = "boolean default false")
        Boolean startTimeEnabled,

        LocalDate dueDate,
        LocalDateTime dueTime,
        @Column(columnDefinition = "boolean default false")
        Boolean dueTimeEnabled
) {
        public Schedule {
                startTimeEnabled = startTimeEnabled == null ? false : startTimeEnabled;
                dueTimeEnabled = dueTimeEnabled == null ? false : dueTimeEnabled;

                validationSchedule(startDate, startTime, startTimeEnabled, dueDate, dueTime, dueTimeEnabled);
        }

        public void validationSchedule(LocalDate startDate,
                                       LocalDateTime startTime,
                                       Boolean startTimeEnabled,
                                       LocalDate dueDate,
                                       LocalDateTime dueTime,
                                       Boolean dueTimeEnabled) {
                if (startDate != null && dueDate != null && dueDate.isBefore(startDate)) {
                        throw new BaseException(BaseResponseStatus.INVALID_DATE_ORDER);
                }
        }
}

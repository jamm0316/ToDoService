package com.todoservice.greencatsoftware.domain.project.presentation.dto;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ProjectCreateRequest(
        Long colorId,  //프로젝트 컬러

        @NotBlank(message = "프로젝트 이름은 필수입니다.")
        String name,  //프로젝트 이름

        @NotNull(message = "상태값은 필수 입니다.")
        Status status,  //프로젝트 상태
        LocalDate startDate,  //시작 날짜
        LocalDate endDate,  //끝나는 날짜
        LocalDate actualEndDate,  //실제 끝낸 날짜
        String description,  //설명

        @NotNull(message = "공개 여부는 필수입니다.")
        Boolean isPublic,  //공개 여부

        @NotNull(message = "공개 범위는 필수입니다.")
        Visibility visibility  //공개 범위
) {
    public ProjectCreateRequest {
        // description이 빈 문자열이면 null로 정규화
        if (description != null && description.isBlank()) description = null;

        // 날짜 일관성 검증
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new BaseException(BaseResponseStatus.END_DATE_BEFORE_START_DATE);
        }

        if (startDate != null && actualEndDate != null && actualEndDate.isBefore(startDate)) {
            throw new BaseException(BaseResponseStatus.ACTUAL_END_DATE_BEFORE_START_DATE);
        }
    }
}

package com.todoservice.greencatsoftware.domain.project.dto;

import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewProjectDTO {
    private Color color;  //프로젝트 컬러

    @NotNull(message = "프로젝트 이름은 필수 입니다.")
    private String name;  //프로젝트 이름

    @NotNull(message = "상태값은 필수 입니다.")
    private Status status;  //프로젝트 상태
    private String startDate;  //시작 날짜
    private String endDate;  //끝나는 날짜
    private String actualEndDate;  //실제 끝낸 날짜
    private String description;  //설명

    @NotNull(message = "공개 여부는 필수입니다.")
    private Boolean isPublic;  //공개 여부

    @NotNull(message = "공개 범위는 필수입니다.")
    private Visibility visibility;  //공개 범위
}

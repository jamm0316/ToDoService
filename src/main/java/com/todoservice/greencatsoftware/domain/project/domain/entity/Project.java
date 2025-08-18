package com.todoservice.greencatsoftware.domain.project.domain.entity;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.common.superEntity.SuperEntity;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "color_id는 필수 입니다.")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @NotNull(message = "프로젝트 이름은 필수 입니다.")
    private String name;

    @NotNull(message = "상태값은 필수 입니다.")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'SCHEDULE'")
    private Status status;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate actualEndDate;

    private String description;

    @NotNull(message = "공개 여부는 필수입니다.")
    @Column(columnDefinition = "boolean default true")
    private Boolean isPublic;

    @NotNull(message = "공개 범위는 필수입니다.")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) default 'PRIVATE'")
    private Visibility visibility;

    public Project(Color color,
                   String name,
                   Status status,
                   LocalDate startDate,
                   LocalDate endDate,
                   LocalDate actualEndDate,
                   String description,
                   Boolean isPublic,
                   Visibility visibility) {

        validateDomainInvariants(color, name, status, startDate, endDate, actualEndDate, description, isPublic, visibility);

        this.color = color;
        this.name = name.trim();
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.actualEndDate = actualEndDate;
        this.description = description != null ? description.trim() : null;
        this.isPublic = isPublic;
        this.visibility = visibility;
    }

    public void validateDomainInvariants(Color color,
                                         String name,
                                         Status status,
                                         LocalDate startDate,
                                         LocalDate endDate,
                                         LocalDate actualEndDate,
                                         String description,
                                         Boolean isPublic,
                                         Visibility visibility) {
        if (color == null) {
            throw new BaseException(BaseResponseStatus.MISSING_COLOR_FOR_TASK);
        }

        if (name == null || name.trim().isEmpty()) {
            throw new BaseException(BaseResponseStatus.MISSING_TITLE_FOR_TASK);
        }

        if (name.length() > 100) {
            throw new BaseException(BaseResponseStatus.TITLE_EXCEEDS_LIMIT_FOR_PROJECT);
        }

        if (status == null) {
            throw new BaseException(BaseResponseStatus.MISSING_STATUS_FOR_TASK);
        }

        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new BaseException(BaseResponseStatus.END_DATE_BEFORE_START_DATE);
        }

        if (startDate != null && actualEndDate != null && actualEndDate.isBefore(startDate)) {
            throw new BaseException(BaseResponseStatus.ACTUAL_END_DATE_BEFORE_START_DATE);
        }

        if (isPublic == null) {
            throw new BaseException(BaseResponseStatus.MISSING_IS_PUBLIC_FOR_PROJECT);
        }

        if (visibility == null) {
            throw new BaseException(BaseResponseStatus.MISSING_VISIBILITY_FOR_PROJECT);
        }
    }

    public static Project create(Color color, String name, Status status,
                                 String description, Boolean isPublic, Visibility visibility) {
        return new Project(color, name, status, null, null, null, description, isPublic, visibility);
    }

    public static Project createWithSchedule(Color color, String name, Status status,
                                 LocalDate startDate, LocalDate endDate, LocalDate actualEndDate,
                                 String description, Boolean isPublic, Visibility visibility) {
        return new Project(color, name, status, startDate, endDate, actualEndDate, description, isPublic, visibility);
    }

    //todo: update 로직 추
    public void changeColor() {
    }

    public void changeName() {
    }

    public void changeStatus() {
    }

    public void changeStartDate() {
    }

    public void changeEndDate() {
    }

    public void changeActualEndDate() {
    }

    public void changeDescription() {
    }

    public void changeIsPublic() {
    }

    public void changeVisibility() {
    }

}

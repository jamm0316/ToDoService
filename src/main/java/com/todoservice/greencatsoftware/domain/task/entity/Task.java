package com.todoservice.greencatsoftware.domain.task.entity;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.enums.DayLabel;
import com.todoservice.greencatsoftware.common.enums.Priority;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.common.superEntity.SuperEntity;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.project.entity.Project;
import com.todoservice.greencatsoftware.domain.task.vo.Schedule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class Task extends SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "프로젝트 id는 필수 입니다.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @NotNull(message = "color는 필수 입니다.")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @NotNull(message = "priority는 필수 입니다.")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'HIGH'")
    private Priority priority;

    @NotNull(message = "제목은 필수입니다.")
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private DayLabel dayLabel;

    @Embedded
    private Schedule schedule;

    @NotNull(message = "상태는 필수 입니다.")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'SCHEDULE'")
    private Status status;

    public Task(Project project,
                Color color,
                Priority priority,
                String title,
                String description,
                DayLabel dayLabel,
                Schedule schedule,
                Status status) {

        validateDomainInvariants(project, color, priority, title, status);

        this.project = project;
        this.color = color;
        this.priority = priority;
        this.title = title.trim();
        this.description = description != null ? description.trim() : null;
        this.dayLabel = dayLabel;
        this.schedule = schedule != null ? schedule : Schedule.noSchedule();
        this.status = status;
    }

    private void validateDomainInvariants(Project project,
                                          Color color,
                                          Priority priority,
                                          String title,
                                          Status status) {
        if (project == null) {
            throw new BaseException(BaseResponseStatus.MISSING_PROJECT_FOR_TASK);
        }

        if (color == null) {
            throw new BaseException(BaseResponseStatus.MISSING_COLOR_FOR_TASK);
        }

        if (title == null || title.trim().isEmpty()) {
            throw new BaseException(BaseResponseStatus.MISSING_TITLE_FOR_TASK);
        }

        if (title.length() > 100) {
            throw new BaseException(BaseResponseStatus.TITLE_EXCEEDS_LIMIT);
        }

        if (priority == null) {
            throw new BaseException(BaseResponseStatus.MISSING_PRIORITY_FOR_TASK);
        }

        if (status == null) {
            throw new BaseException(BaseResponseStatus.MISSING_STATUS_FOR_TASK);
        }
    }
}

package com.todoservice.greencatsoftware.domain.task.domain.entity;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.enums.DayLabel;
import com.todoservice.greencatsoftware.common.enums.Priority;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.common.superEntity.SuperEntity;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.project.entity.Project;
import com.todoservice.greencatsoftware.domain.task.domain.vo.Schedule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static Task create(Project project, Color color, Priority priority, String title, String description,
                              DayLabel dayLabel, Status status
                              ) {
        return new Task(project, color, priority, title, description, dayLabel, null, status);
    }

    public static Task createWithSchedule(Project project, Color color, Priority priority, String title, String description,
                              DayLabel dayLabel, Schedule schedule, Status status
                              ) {
        return new Task(project, color, priority, title, description, dayLabel, schedule, status);
    }

    public void changeProject(Project project) {
        if (project == null) {
            throw new BaseException(BaseResponseStatus.MISSING_PROJECT_FOR_TASK);
        }
        this.project = project;
    }

    public void changeColor(Color color) {
        if (color == null) {
            throw new BaseException(BaseResponseStatus.MISSING_COLOR_FOR_TASK);
        }
        this.color = color;
    }

    public void changePriority(Priority priority) {
        if (priority == null) {
            throw new BaseException(BaseResponseStatus.MISSING_PRIORITY_FOR_TASK);
        }
        this.priority = priority;
    }

    public void changeTitle(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new BaseException(BaseResponseStatus.MISSING_TITLE_FOR_TASK);
        }

        if (name.length() > 100) {
            throw new BaseException(BaseResponseStatus.TITLE_EXCEEDS_LIMIT);
        }
        this.title = name.trim();
    }

    public void changeDescription(String description) {
        if (description != null && !description.isBlank()) {
            this.description = description.trim();
        }
    }

    public void changeDayLabel(DayLabel dayLabel) {
        if (dayLabel != null) {
            this.dayLabel = dayLabel;
        }
    }

    public void changeSchedule(Schedule schedule) {
        if (schedule == null) {
            this.schedule = Schedule.noSchedule();
        } else {
            this.schedule = schedule;
        }
    }

    public void changeStatus(Status status) {
        if (status == null) {
            throw new BaseException(BaseResponseStatus.MISSING_STATUS_FOR_TASK);
        }
        this.status = status;
    }
}

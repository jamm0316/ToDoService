package com.todoservice.greencatsoftware.domain.task.application;

import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.model.ColorService;
import com.todoservice.greencatsoftware.domain.project.entity.Project;
import com.todoservice.greencatsoftware.domain.project.model.ProjectService;
import com.todoservice.greencatsoftware.domain.task.domain.entity.Task;
import com.todoservice.greencatsoftware.domain.task.domain.vo.Schedule;
import com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskFactory {
    private final ProjectService projectService;
    private final ColorService colorService;

    public Task createTask(TaskCreateRequest request) {
        Project project = projectService.getProjectByIdOrThrow(request.projectId());
        Color color = colorService.getColorByIdOrThrow(request.colorId());
        Schedule schedule = Schedule.of(
                request.schedule().startDate(), request.schedule().startTime(), request.schedule().startTimeEnabled(),
                request.schedule().dueDate(), request.schedule().dueTime(), request.schedule().dueTimeEnabled());

        return (schedule != null)
                ? Task.createWithSchedule(project, color, request.priority(), request.title(), request.description(),
                request.dayLabel(), schedule, request.status())
                : Task.create(project, color, request.priority(), request.title(), request.description(),
                request.dayLabel(), request.status());
    }
}

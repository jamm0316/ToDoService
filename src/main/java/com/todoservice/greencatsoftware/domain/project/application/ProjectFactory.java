package com.todoservice.greencatsoftware.domain.project.application;

import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.model.ColorService;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.presentation.dto.ProjectCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectFactory {
    private final ColorService colorService;

    public Project createProject(ProjectCreateRequest request) {
        Color color = colorService.getColorByIdOrThrow(request.colorId());
        return Project.createWithSchedule(color, request.name(), request.status(),
                request.startDate(), request.endDate(), request.actualEndDate(),
                request.description(), request.isPublic(), request.visibility());
    }
}

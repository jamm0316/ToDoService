package com.todoservice.greencatsoftware.domain.project.application;

import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.application.ColorService;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.domain.vo.Period;
import com.todoservice.greencatsoftware.domain.project.presentation.dto.ProjectCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectFactory {
    private final ColorService colorService;

    public Project createProject(ProjectCreateRequest request) {
        Color color = colorService.getColorByIdOrThrow(request.colorId());
        Period period = Period.of(request.period().startDate(), request.period().endDate(),
                                  request.period().actualEndDate());

        return (period.isNull())
                ? Project.create(color, request.name(), request.status(),
                request.description(), request.isPublic(), request.visibility())

                : Project.createWithPeriod(color, request.name(), request.status(),
                request.period().startDate(), request.period().endDate(), request.period().actualEndDate(),
                request.description(), request.isPublic(), request.visibility());
    }
}
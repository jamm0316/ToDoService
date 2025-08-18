package com.todoservice.greencatsoftware.domain.project.application;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.model.ColorService;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.domain.port.ProjectRepository;
import com.todoservice.greencatsoftware.domain.project.domain.vo.Period;
import com.todoservice.greencatsoftware.domain.project.presentation.dto.ProjectCreateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectFactory projectFactory;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final ColorService colorService;

    public List<Project> listProject() {
        return projectRepository.findAll();
    }

    public Project getProjectByIdOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_PROJECT));
    }

    @Transactional
    public Project createProject(ProjectCreateRequest newProjectDTO) {
        return projectRepository.save(projectFactory.createProject(newProjectDTO));
    }

    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Transactional
    public Project updateProject(ProjectCreateRequest request, Long id) {
        Project project = getProjectByIdOrThrow(id);
        Color color = (request.colorId() != null)
                ? colorService.getColorByIdOrThrow(request.colorId())
                : project.getColor();

        Period period = (request.period() != null)
                ? Period.of(request.period().startDate(), request.period().endDate(), request.period().actualEndDate())
                : project.getPeriod();

        project.changeColor(color);
        project.changeName(request.name());
        project.changeStatus(request.status());
        project.changePeriod(period);
        project.changeDescription(request.description());
        project.changeIsPublic(request.isPublic());
        project.changeVisibility(request.visibility());

        return project;
    }
}
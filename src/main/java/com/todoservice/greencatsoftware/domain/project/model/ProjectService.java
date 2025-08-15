package com.todoservice.greencatsoftware.domain.project.model;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.model.ColorService;
import com.todoservice.greencatsoftware.domain.project.dto.ProjectCreateRequest;
import com.todoservice.greencatsoftware.domain.project.entity.Project;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
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
        return projectRepository.save(toEntity(newProjectDTO));
    }

    private Project toEntity(ProjectCreateRequest newProjectDTO) {
        return modelMapper.map(newProjectDTO, Project.class);
    };

    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Transactional
    public Project updateProject(ProjectCreateRequest newProjectDTO, Long id) {
        Project project = getProjectByIdOrThrow(id);
        applyProjectUpdate(project, newProjectDTO);
        applyProjectRelations(project, newProjectDTO);
        return projectRepository.save(project);
    }

    private void applyProjectUpdate(Project project, ProjectCreateRequest newProjectDTO) {
        if (newProjectDTO.name() != null) project.setName(newProjectDTO.name());
        if (newProjectDTO.status() != null) project.setStatus(newProjectDTO.status());
        if (newProjectDTO.startDate() != null) project.setStartDate(newProjectDTO.startDate());
        if (newProjectDTO.endDate() != null) project.setEndDate(newProjectDTO.endDate());
        if (newProjectDTO.actualEndDate() != null) project.setActualEndDate(newProjectDTO.actualEndDate());
        if (newProjectDTO.description() != null) project.setDescription(newProjectDTO.description());
        if (newProjectDTO.isPublic() != null) project.setIsPublic(newProjectDTO.isPublic());
        if (newProjectDTO.visibility() != null) project.setVisibility(newProjectDTO.visibility());
    }

    private void applyProjectRelations(Project project, ProjectCreateRequest newProjectDTO) {
        if (newProjectDTO.colorId() != null) {
            Color color = colorService.getColorByIdOrThrow(newProjectDTO.colorId());
            project.setColor(color);
        }
    }
}

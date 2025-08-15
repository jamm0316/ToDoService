package com.todoservice.greencatsoftware.domain.project.model;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.project.dto.NewProjectDTO;
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

    public List<Project> listProject() {
        return projectRepository.findAll();
    }

    public Project getProjectByIdOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_PROJECT));
    }

    @Transactional
    public Project createProject(NewProjectDTO newProjectDTO) {
        return projectRepository.save(toEntity(newProjectDTO));
    }

    private Project toEntity(NewProjectDTO newProjectDTO) {
        return modelMapper.map(newProjectDTO, Project.class);
    };

    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Transactional
    public Project updateProject(NewProjectDTO newProjectDTO, Long id) {
        Project project = getProjectByIdOrThrow(id);
        applyProjectUpdate(project, newProjectDTO);
        applyProjectRelations(project, newProjectDTO);
        return projectRepository.save(project);
    }

    private void applyProjectUpdate(Project project, NewProjectDTO newProjectDTO) {
        if (newProjectDTO.getName() != null) project.setName(newProjectDTO.getName());
        if (newProjectDTO.getStatus() != null) project.setStatus(newProjectDTO.getStatus());
        if (newProjectDTO.getStartDate() != null) project.setStartDate(newProjectDTO.getStartDate());
        if (newProjectDTO.getEndDate() != null) project.setEndDate(newProjectDTO.getEndDate());
        if (newProjectDTO.getActualEndDate() != null) project.setActualEndDate(newProjectDTO.getActualEndDate());
        if (newProjectDTO.getDescription() != null) project.setDescription(newProjectDTO.getDescription());
        if (newProjectDTO.getIsPublic() != null) project.setIsPublic(newProjectDTO.getIsPublic());
        if (newProjectDTO.getVisibility() != null) project.setVisibility(newProjectDTO.getVisibility());
    }

    private void applyProjectRelations(Project project, NewProjectDTO newProjectDTO) {
        if (newProjectDTO.getColor() != null) project.setColor(newProjectDTO.getColor());
    }
}

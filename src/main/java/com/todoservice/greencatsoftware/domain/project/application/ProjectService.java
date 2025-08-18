package com.todoservice.greencatsoftware.domain.project.application;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.color.model.ColorService;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.domain.port.ProjectRepository;
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
}

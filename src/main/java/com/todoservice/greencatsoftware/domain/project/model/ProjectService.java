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
}

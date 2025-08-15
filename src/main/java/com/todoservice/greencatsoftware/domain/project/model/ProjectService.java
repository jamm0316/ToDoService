package com.todoservice.greencatsoftware.domain.project.model;

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
}

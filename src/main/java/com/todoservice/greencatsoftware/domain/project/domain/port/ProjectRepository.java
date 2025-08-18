package com.todoservice.greencatsoftware.domain.project.domain.port;

import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    List<Project> findAll();
    Project save(Project project);
    Optional<Project> findById(Long id);
    void deleteById(Long id);
}

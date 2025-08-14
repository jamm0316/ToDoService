package com.todoservice.greencatsoftware.domain.project.model;

import com.todoservice.greencatsoftware.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}

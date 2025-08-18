package com.todoservice.greencatsoftware.domain.project.infrastructure.persistence;

import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.domain.port.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface SpringDataProjectJpaRepository extends JpaRepository<Project, Long> {

    @Repository
    @RequiredArgsConstructor
    class ProjectRepositoryImpl implements ProjectRepository {
        private final SpringDataProjectJpaRepository jpa;

        @Override
        public List<Project> findAll() {
            return jpa.findAll();
        }

        @Override
        public Project save(Project project) {
            return jpa.save(project);
        }

        @Override
        public Optional<Project> findById(Long id) {
            return jpa.findById(id);
        }

        @Override
        public void deleteById(Long id) {
            jpa.deleteById(id);
        }
    }
}

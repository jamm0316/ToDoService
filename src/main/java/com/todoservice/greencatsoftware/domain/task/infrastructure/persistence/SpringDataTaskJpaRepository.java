package com.todoservice.greencatsoftware.domain.task.infrastructure.persistence;

import com.todoservice.greencatsoftware.domain.task.domain.entity.Task;
import com.todoservice.greencatsoftware.domain.task.domain.port.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface SpringDataTaskJpaRepository extends JpaRepository<Task, Long> {

    @Repository
    @RequiredArgsConstructor
    class TaskRepositoryImpl implements TaskRepository {
        private final SpringDataTaskJpaRepository jpa;

        @Override
        public List<Task> findAll() {
            return jpa.findAll();
        }

        @Override
        public Task save(Task task) {
            return jpa.save(task);
        }

        @Override
        public void deleteById(Long id) {
            jpa.deleteById(id);
        }

        @Override
        public Optional<Task> findById(Long id) {
            return jpa.findById(id);
        }
    }
}

package com.todoservice.greencatsoftware.domain.task;

import com.todoservice.greencatsoftware.domain.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

package com.todoservice.greencatsoftware.domain.task.domain.port;

import com.todoservice.greencatsoftware.domain.task.domain.entity.Task;
import com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskSummaryResponse;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();
    List<TaskSummaryResponse> summaryListTask();
    Task save(Task task);
    Optional<Task> findById(Long id);
    void deleteById(Long id);
}

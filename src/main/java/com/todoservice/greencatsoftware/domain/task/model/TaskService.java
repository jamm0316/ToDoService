package com.todoservice.greencatsoftware.domain.task.model;

import com.todoservice.greencatsoftware.domain.task.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> listTask() {
        return taskRepository.findAll();
    }
}

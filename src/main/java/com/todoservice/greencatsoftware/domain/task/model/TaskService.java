package com.todoservice.greencatsoftware.domain.task.model;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.task.dto.TaskCreateRequest;
import com.todoservice.greencatsoftware.domain.task.entity.Task;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public List<Task> listTask() {
        return taskRepository.findAll();
    }

    public Task getTaskByIdOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_TASK));
    }

    public Task createTask(TaskCreateRequest newTaskDTO) {
        return taskRepository.save(toEntity(newTaskDTO));
    }

    private Task toEntity(TaskCreateRequest newTaskDTO) {
        return modelMapper.map(newTaskDTO, Task.class);
    }
}

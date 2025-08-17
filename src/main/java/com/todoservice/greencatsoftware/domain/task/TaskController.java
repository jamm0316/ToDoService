package com.todoservice.greencatsoftware.domain.task;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponse;
import com.todoservice.greencatsoftware.domain.task.dto.TaskCreateRequest;
import com.todoservice.greencatsoftware.domain.task.entity.Task;
import com.todoservice.greencatsoftware.domain.task.model.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1//task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("")
    public BaseResponse<List<Task>> listTask() {
        return new BaseResponse<>(taskService.listTask());
    }

    @PostMapping("")
    public BaseResponse<Task> createColor(TaskCreateRequest newTaskDTO) {
        return new BaseResponse<>(taskService.createTask(newTaskDTO));
    }

    @PatchMapping("/{id}")
    public BaseResponse<Task> updateColor(TaskCreateRequest newProjectDTO, @PathVariable Long id) {
        return new BaseResponse<>(taskService.updateTask(newProjectDTO, id));
    }
}

package com.todoservice.greencatsoftware.domain.task.presentation;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponse;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskCreateRequest;
import com.todoservice.greencatsoftware.domain.task.domain.entity.Task;
import com.todoservice.greencatsoftware.domain.task.application.TaskService;
import com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskSummaryResponse;
import com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("")
    public BaseResponse<List<Task>> listTask() {
        return new BaseResponse<>(taskService.listTask());
    }

    @GetMapping("/summary")
    public BaseResponse<List<TaskSummaryResponse>> summaryListTask() {
        return new BaseResponse<>(taskService.summaryListTask());
    }

    @GetMapping("/today")
    public BaseResponse<List<TaskSummaryResponse>> todayListTask() {
        return new BaseResponse<>(taskService.todayListTask(LocalDate.now()));
    }

    @PostMapping("")
    public BaseResponse<Task> createTask(@Valid @RequestBody TaskCreateRequest newTaskDTO) {
        return new BaseResponse<>(taskService.createTask(newTaskDTO));
    }

    @PatchMapping("/{id}")
    public BaseResponse<Task> updateTask(TaskUpdateRequest updateTaskDTO, @PathVariable Long id) {
        return new BaseResponse<>(taskService.updateTask(updateTaskDTO, id));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new BaseResponse<>();
    }

    @PatchMapping("/{id}/status")
    public void updateTaskStatus(@PathVariable Long id, @RequestParam Status newStatus) {
        taskService.updateTaskStatus(id, newStatus);
    }
}

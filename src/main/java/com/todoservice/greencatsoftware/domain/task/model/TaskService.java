package com.todoservice.greencatsoftware.domain.task.model;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.color.model.ColorService;
import com.todoservice.greencatsoftware.domain.project.model.ProjectService;
import com.todoservice.greencatsoftware.domain.task.dto.TaskCreateRequest;
import com.todoservice.greencatsoftware.domain.task.entity.Task;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final ProjectService projectService;
    private final ColorService colorService;

    public List<Task> listTask() {
        return taskRepository.findAll();
    }

    public Task getTaskByIdOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_TASK));
    }

    @Transactional
    public Task createTask(TaskCreateRequest newTaskDTO) {
        return taskRepository.save(toEntity(newTaskDTO));
    }

    private Task toEntity(TaskCreateRequest newTaskDTO) {
        return modelMapper.map(newTaskDTO, Task.class);
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public Task updateTask(TaskCreateRequest newTaskDTO, Long id) {
        Task task = getTaskByIdOrThrow(id);
        applyTaskUpdate(task, newTaskDTO);
        applyTaskRelations(task, newTaskDTO);
        return taskRepository.save(task);
    }

    private void applyTaskUpdate(Task task, TaskCreateRequest newTaskDTO) {
        if(newTaskDTO.priority() != null) task.setPriority(newTaskDTO.priority());
        if(newTaskDTO.title() != null) task.setTitle(newTaskDTO.title());
        if(newTaskDTO.description() != null) task.setDescription(newTaskDTO.description());
        if(newTaskDTO.dayLabel() != null) task.setDayLabel(newTaskDTO.dayLabel());
        if(newTaskDTO.startDate() != null) task.setStartDate(newTaskDTO.startDate());
        if(newTaskDTO.startTime() != null) task.setStartTime(newTaskDTO.startTime());
        if(newTaskDTO.startTimeEnabled() != null) task.setStartTimeEnabled(newTaskDTO.startTimeEnabled());
        if(newTaskDTO.dueDate() != null) task.setDueDate(newTaskDTO.dueDate());
        if(newTaskDTO.dueTime() != null) task.setDueTime(newTaskDTO.dueTime());
        if(newTaskDTO.dueTimeEnabled() != null) task.setDueTimeEnabled(newTaskDTO.dueTimeEnabled());
        if(newTaskDTO.status() != null) task.setStatus(newTaskDTO.status());
    }

    private void applyTaskRelations(Task task, TaskCreateRequest newTaskDTO) {
        if(newTaskDTO.projectId() != null) task.setProject(projectService.getProjectByIdOrThrow(newTaskDTO.projectId()));
        if(newTaskDTO.colorId() != null) task.setColor(colorService.getColorByIdOrThrow(newTaskDTO.colorId()));
    }

    @Transactional
    public void updateTaskStatus(Long id, Status newStatus) {
        Task task = getTaskByIdOrThrow(id);
        task.setStatus(newStatus);
    }

    @Transactional
    public void updateTaskTimeEnable(Long id, Boolean newStartTimeEnabled, Boolean newDueTimeEnabled) {
        Task task = getTaskByIdOrThrow(id);
        if (newStartTimeEnabled != null) {
            task.setStartTimeEnabled(newStartTimeEnabled);
        }
        if (newDueTimeEnabled != null) {
            task.setDueTimeEnabled(newDueTimeEnabled);
        }
    }
}

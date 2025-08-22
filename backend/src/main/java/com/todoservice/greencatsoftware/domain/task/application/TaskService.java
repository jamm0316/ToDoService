package com.todoservice.greencatsoftware.domain.task.application;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.application.ColorService;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.application.ProjectService;
import com.todoservice.greencatsoftware.domain.task.domain.entity.Task;
import com.todoservice.greencatsoftware.domain.task.domain.port.TaskRepository;
import com.todoservice.greencatsoftware.domain.task.domain.vo.Schedule;
import com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskCreateRequest;
import com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskSummaryResponse;
import com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {
    private final TaskFactory taskFactory;
    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final ColorService colorService;

    public List<Task> listTask() {
        return taskRepository.findAll();
    }

    public List<TaskSummaryResponse> summaryListTask() {
        return taskRepository.summaryListTask();
    }

    public Task getTaskByIdOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_TASK));
    }

    @Transactional
    public Task createTask(TaskCreateRequest newTaskDTO) {
        return taskRepository.save(taskFactory.createTask(newTaskDTO));
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public Task updateTask(TaskUpdateRequest request, Long id) {
        Task task = getTaskByIdOrThrow(id);
        Project project = (request.projectId() != null)
                ? projectService.getProjectByIdOrThrow(request.projectId())
                : task.getProject();
        Schedule schedule = (request.schedule() != null)
                ? request.schedule()
                : task.getSchedule();

        task.changeProject(project);
        task.changePriority(request.priority());
        task.changeTitle(request.title());
        task.changeDescription(request.description());
        task.changeDayLabel(request.dayLabel());
        task.changeSchedule(schedule);
        task.changeStatus(request.status());

        return task;
    }

    @Transactional
    public Task updateTaskStatus(Long id, Status status) {
        Task task = getTaskByIdOrThrow(id);
        task.changeStatus(status);
        return task;
    }

    //todo: 작업 대량 생성
    public void bulkCreateTasks(List<TaskCreateRequest> newTaskDTOList) {
        throw new UnsupportedOperationException("TODO: 작업 대량 생성 작성");
    }

    //todo: 작업 대량 수정
    public void bulkUpdateTasks(List<TaskCreateRequest> newTaskDTOList) {
        throw new UnsupportedOperationException("TODO: 작업 대량 수정 작성");
    }

    //todo: 작업 대량 삭제
    public void bulkDeleteTasks(List<Long> taskIdList) {
        throw new UnsupportedOperationException("TODO: 작업 대량 삭제 작성");
    }
}

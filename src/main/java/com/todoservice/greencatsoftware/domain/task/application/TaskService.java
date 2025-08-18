package com.todoservice.greencatsoftware.domain.task.application;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.model.ColorService;
import com.todoservice.greencatsoftware.domain.project.entity.Project;
import com.todoservice.greencatsoftware.domain.project.model.ProjectService;
import com.todoservice.greencatsoftware.domain.task.domain.entity.Task;
import com.todoservice.greencatsoftware.domain.task.domain.port.TaskRepository;
import com.todoservice.greencatsoftware.domain.task.domain.vo.Schedule;
import com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskCreateRequest;
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

    //todo: entity에서 entity 수정 로직 만들기
    //todo: factory에서 update로직 컨트롤 메서드 만들기
    //todo: factory를 통해 service는 update 로직 수행
    @Transactional
    public Task updateTask(TaskUpdateRequest request, Long id) {
        Task task = getTaskByIdOrThrow(id);
        Project project = (request.projectId() != null)
                ? projectService.getProjectByIdOrThrow(request.projectId())
                : task.getProject();
        Color color = (request.colorId() != null)
                ? colorService.getColorByIdOrThrow(request.colorId())
                : task.getColor();
        Schedule schedule = (request.schedule() != null)
                ? request.schedule()
                : task.getSchedule();

        task.changeProject(project);
        task.changeColor(color);
        task.changePriority(request.priority());
        task.changeTitle(request.title());
        task.changeDescription(request.description());
        task.changeDayLabel(request.dayLabel());
        task.changeSchedule(schedule);
        task.changeStatus(request.status());

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

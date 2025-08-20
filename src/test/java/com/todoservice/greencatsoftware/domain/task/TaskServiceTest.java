package com.todoservice.greencatsoftware.domain.task;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.color.application.ColorService;
import com.todoservice.greencatsoftware.domain.project.application.ProjectService;
import com.todoservice.greencatsoftware.domain.task.application.TaskFactory;
import com.todoservice.greencatsoftware.domain.task.application.TaskService;
import com.todoservice.greencatsoftware.domain.task.domain.entity.Task;
import com.todoservice.greencatsoftware.domain.task.domain.port.TaskRepository;
import com.todoservice.greencatsoftware.domain.task.presentation.dto.TaskCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock private TaskRepository taskRepository;
    @Mock private ProjectService projectService;
    @Mock private ColorService colorService;
    @Mock private TaskFactory factory;
    @InjectMocks private TaskService taskService;

    @Test
    @DisplayName("listTask: 전체 조회")
    public void listTask() throws Exception {
        //when
        when(taskRepository.findAll()).thenReturn(List.of(mock(Task.class), mock(Task.class)));

        //then
        assertThat(taskService.listTask()).hasSize(2);
        verify(taskRepository).findAll();
    }

    @Test
    @DisplayName("getTaskByIdOrThrow: 존재, 반환 / 미존재, BaseException 반환")
    public void getTaskByIdOrThrow() throws Exception {
        //given
        Task task = mock(Task.class);

        //when
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.findById(9L)).thenReturn(Optional.empty());

        //then
        assertThat(taskService.getTaskByIdOrThrow(1L)).isSameAs(task);
        assertThatThrownBy(() -> taskService.getTaskByIdOrThrow(9L))
                .isInstanceOf(BaseException.class)
                .hasMessage(BaseResponseStatus.NOT_FOUND_TASK.getMessage());
    }

    @Test
    @DisplayName("createTask: Factory가 조립한 엔티티를 save한다.")
    public void createTask() throws Exception {
        //given
        TaskCreateRequest request = mock(TaskCreateRequest.class);
        Task task = mock(Task.class);

        //when
        when(factory.createTask(request)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        Task saved = taskService.createTask(request);

        //then
        assertThat(saved).isSameAs(task);
        verify(factory).createTask(request);
        verify(taskRepository).save(task);
    }
}

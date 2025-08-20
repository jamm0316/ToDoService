package com.todoservice.greencatsoftware.domain.task;

import com.todoservice.greencatsoftware.domain.color.application.ColorService;
import com.todoservice.greencatsoftware.domain.project.application.ProjectService;
import com.todoservice.greencatsoftware.domain.task.application.TaskFactory;
import com.todoservice.greencatsoftware.domain.task.application.TaskService;
import com.todoservice.greencatsoftware.domain.task.domain.entity.Task;
import com.todoservice.greencatsoftware.domain.task.domain.port.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
}

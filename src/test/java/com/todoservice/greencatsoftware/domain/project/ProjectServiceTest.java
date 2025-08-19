package com.todoservice.greencatsoftware.domain.project;

import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.domain.color.application.ColorService;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.project.application.ProjectService;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.domain.port.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ColorService colorService;

    @InjectMocks
    private ProjectService projectService;

    @Test
    @DisplayName("listProject: 전체 조회")
    public void listProject() throws Exception {
        //given
        Color color = colorService.createColor("RED", "#FF0000");
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        LocalDate actualEndDate = LocalDate.of(2025, 12, 31);
        Project projectA = Project.createWithPeriod(color, "프로젝트 A", Status.SCHEDULE,
                startDate, endDate, actualEndDate, "프로젝트 A입니다", true, Visibility.PRIVATE);
        Project projectB = Project.createWithPeriod(color, "프로젝트 B", Status.SCHEDULE,
                startDate, endDate, actualEndDate, "프로젝트 B입니다", true, Visibility.PRIVATE);

        //when
        when(projectRepository.findAll()).thenReturn(List.of(projectA, projectB));

        //then
        assertThat(projectService.listProject()).hasSize(2);
        verify(projectRepository).findAll();

    }
}

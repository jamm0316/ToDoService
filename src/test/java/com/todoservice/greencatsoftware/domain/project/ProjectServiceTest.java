package com.todoservice.greencatsoftware.domain.project;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.color.application.ColorService;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.project.application.ProjectFactory;
import com.todoservice.greencatsoftware.domain.project.application.ProjectService;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.domain.port.ProjectRepository;
import com.todoservice.greencatsoftware.domain.project.presentation.dto.ProjectCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ColorService colorService;

    @Mock
    private ProjectFactory factory;

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
    
    @Test
    @DisplayName("getProjectByIdThrow: 존재, 반환 / 미존재, BaseException 반환")
    public void getByIdOrThrow() throws Exception {
        //given
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        LocalDate actualEndDate = LocalDate.of(2025, 12, 31);
        Project project = Project.createWithPeriod(Color.create("RED", "#FF000000"), "프로젝트 A", Status.SCHEDULE,
                startDate, endDate, actualEndDate, "프로젝트 A입니다", true, Visibility.PRIVATE);

        //when
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.findById(9L)).thenReturn(java.util.Optional.empty());

        //then
        assertThat(projectService.getProjectByIdOrThrow(1L)).isEqualTo(project);
        assertThatThrownBy(() -> projectService.getProjectByIdOrThrow(9L))
                .isInstanceOf(BaseException.class)
                .hasMessage(BaseResponseStatus.NOT_FOUND_PROJECT.getMessage());
    }

    @Test
    @DisplayName("createProject: Factory가 조립한 엔티티를 save한다.")
    public void createProject() throws Exception {
        //given
        ProjectCreateRequest request = mock(ProjectCreateRequest.class);
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        LocalDate actualEndDate = LocalDate.of(2025, 12, 31);
        Project project = Project.createWithPeriod(Color.create("RED", "#FF000000"), "프로젝트 A", Status.SCHEDULE,
                startDate, endDate, actualEndDate, "프로젝트 A입니다", true, Visibility.PRIVATE);

        //when
        when(factory.createProject(request)).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project saved = projectService.createProject(request);

        //then
        assertThat(saved).isEqualTo(project);
        verify(factory).createProject(request);
        verify(projectRepository).save(project);
    }

    @Test
    @DisplayName("deleteProject: 삭제")
    public void delete() throws Exception {
        //then
        projectService.deleteProjectById(3L);
        verify(projectRepository).deleteById(3L);
    }

    @Test
    @DisplayName("updateProject: 조회 -> 엔티티 내부 상태 변경 (save 호출 없이 반환)")
    public void updateProject() throws Exception {
        //given
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        LocalDate actualEndDate = LocalDate.of(2025, 12, 31);
        Project project = Project.createWithPeriod(Color.create("RED", "#FF000000"), "프로젝트 A", Status.SCHEDULE,
                startDate, endDate, actualEndDate, "프로젝트 A입니다", true, Visibility.PRIVATE);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        ProjectCreateRequest request = mock(ProjectCreateRequest.class);
        when(request.colorId()).thenReturn(99L);
        when(request.name()).thenReturn("newTitle");
        when(request.status()).thenReturn(Status.COMPLETED);
        when(request.period()).thenReturn(null);
        when(request.description()).thenReturn("newDescription");
        when(request.isPublic()).thenReturn(false);
        when(request.visibility()).thenReturn(Visibility.TEAM);

        //when
        when(colorService.getColorByIdOrThrow(99L)).thenReturn(Color.create("BLUE", "#0000FF"));
        Project update = projectService.updateProject(request, 1L);

        //then
        assertThat(update.getColor().getName()).isEqualTo("BLUE");
        assertThat(update.getColor().getHexCode()).isEqualTo("#0000FF");
        assertThat(update.getName()).isEqualTo("newTitle");
        assertThat(update.getStatus()).isEqualTo(Status.COMPLETED);
        assertThat(update.getDescription()).isEqualTo("newDescription");
        assertThat(update.getIsPublic()).isFalse();
        assertThat(update.getVisibility()).isEqualTo(Visibility.TEAM);
        verify(projectRepository, never()).save(any());
    }
}
package com.todoservice.greencatsoftware.domain.project;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.model.ColorService;
import com.todoservice.greencatsoftware.domain.project.dto.ProjectCreateRequest;
import com.todoservice.greencatsoftware.domain.project.entity.Project;
import com.todoservice.greencatsoftware.domain.project.model.ProjectRepository;
import com.todoservice.greencatsoftware.domain.project.model.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ColorService colorService;

    @InjectMocks
    private ProjectService projectService;

    private ProjectCreateRequest projectCreateRequest =
            new ProjectCreateRequest(null, "newProject", Status.SCHEDULE,
                    LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 15),
                    null, "newDescription", true, Visibility.PUBLIC);

    @Test
    @DisplayName("모든 프로젝트 목록을 반환한다.")
    public void 모든_프로젝트_목록_반환() throws Exception {
        //given
        Project projectA = new Project();
        projectA.setId(1L);
        projectA.setName("projectA");

        Project projectB = new Project();
        projectB.setId(2L);
        projectB.setName("projectB");

        when(projectRepository.findAll()).thenReturn(Arrays.asList(projectA, projectB));

        //when
        List<Project> projects = projectService.listProject();

        //then
        assertThat(projects).hasSize(2);
        assertThat(projects.get(0).getName()).isEqualTo("projectA");
        assertThat(projects.get(1).getName()).isEqualTo("projectB");
    }

    @Test
    @DisplayName("프로젝트가 존재하면 반환한다.")
    public void 프로젝트_존재시_반환() throws Exception {
        //given
        Project project = new Project();
        project.setId(1L);
        project.setName("projectA");
        when(projectRepository.findById(project.getId())).thenReturn(java.util.Optional.of(project));

        //then
        assertThat(projectService.getProjectByIdOrThrow(1L)).isEqualTo(project);
        assertThat(projectService.getProjectByIdOrThrow(1L).getName()).isEqualTo("projectA");
    }

    @Test
    @DisplayName("프로젝트가 존재하지 않으면 BaseException 반환")
    public void 프로젝트_없으면_BaseException_반환() throws Exception {
        //given
        when(projectRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        //then
        assertThatThrownBy(() -> projectService.getProjectByIdOrThrow(99L))
                .isInstanceOf(BaseException.class)
                .hasMessage(BaseResponseStatus.NOT_FOUND_PROJECT.getMessage());
    }

    @Test
    @DisplayName("DTO를 엔티티로 매핑하고 저장한다")
    public void 엔티티를_매핑하고_저장한다() throws Exception {
        //given
        Project project = new Project();
        project.setName(projectCreateRequest.name());
        project.setStatus(projectCreateRequest.status());
        project.setStartDate(projectCreateRequest.startDate());
        project.setEndDate(projectCreateRequest.endDate());
        project.setIsPublic(projectCreateRequest.isPublic());
        project.setVisibility(projectCreateRequest.visibility());

        when(modelMapper.map(projectCreateRequest, Project.class)).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> {
            Project arg = inv.getArgument(0);
            arg.setId(100L);
            return arg;
        });

        //when
        Project savedProject = projectService.createProject(projectCreateRequest);

        //then
        assertThat(savedProject.getId()).isEqualTo(100L);
        assertThat(savedProject.getName()).isEqualTo("newProject");
        assertThat(savedProject.getStatus()).isEqualTo(Status.SCHEDULE);
        assertThat(savedProject.getStartDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(savedProject.getEndDate()).isEqualTo(LocalDate.of(2025, 1, 15));
        assertThat(savedProject.getIsPublic()).isEqualTo(true);
        assertThat(savedProject.getVisibility()).isEqualTo(Visibility.PUBLIC);
        verify(modelMapper).map(projectCreateRequest, Project.class);
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    @DisplayName("프로젝트 삭제 호출")
    public void 프로젝트_삭제_호출() throws Exception {
        //when
        projectService.deleteProject(100L);

        //then
        verify(projectRepository).deleteById(100L);
    }

    @Test
    @DisplayName("스칼라 필드를 갱신하고 저장한다")
    public void 스칼라_필드_갱신_저장() throws Exception {
        //given
        Project project = new Project();
        project.setId(1L);
        project.setName("projectA");
        project.setDescription("abc");
        project.setStatus(Status.SCHEDULE);
        project.setStartDate(LocalDate.of(2025, 1, 1));
        project.setEndDate(LocalDate.of(2025, 1, 15));
        project.setIsPublic(true);
        project.setVisibility(Visibility.PUBLIC);

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));

        //when
        projectService.updateProject(projectCreateRequest, project.getId());

        //then
        assertThat(project.getName()).isEqualTo("newProject");
        assertThat(project.getDescription()).isEqualTo("newDescription");
        assertThat(project.getStatus()).isEqualTo(Status.SCHEDULE);
        assertThat(project.getStartDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(project.getEndDate()).isEqualTo(LocalDate.of(2025, 1, 15));
        assertThat(project.getIsPublic()).isEqualTo(true);
        assertThat(project.getVisibility()).isEqualTo(Visibility.PUBLIC);

        verify(projectRepository).findById(project.getId());
        verify(colorService, never()).getColorByIdOrThrow(anyLong());
        verify(projectRepository).save(any(Project.class));
    }
}

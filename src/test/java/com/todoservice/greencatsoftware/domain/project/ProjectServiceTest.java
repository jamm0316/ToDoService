package com.todoservice.greencatsoftware.domain.project;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.project.entity.Project;
import com.todoservice.greencatsoftware.domain.project.model.ProjectRepository;
import com.todoservice.greencatsoftware.domain.project.model.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

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
}

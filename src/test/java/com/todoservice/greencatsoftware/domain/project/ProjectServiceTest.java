package com.todoservice.greencatsoftware.domain.project;

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
}

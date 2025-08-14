package com.todoservice.greencatsoftware.domain.project;

import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.domain.color.Color;
import com.todoservice.greencatsoftware.domain.color.ColorRepository;
import com.todoservice.greencatsoftware.domain.project.entity.Project;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ProjectJpaTest {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ColorRepository colorRepository;

    private Color saveColor() {
        Color color = new Color();
        color.setName("RED");
        color.setHexCode("FF0000");
        return colorRepository.saveAndFlush(color);
    }

    @Test
    @DisplayName("프로젝트 저장 조회")
    public void 프로젝트_저장_조회() throws Exception {
        //given
        Color color = saveColor();

        Project project = new Project();
        project.setColor(color);
        project.setName("프로젝트A");
        project.setStatus(Status.SCHEDULE);
        project.setIsPublic(true);
        project.setVisibility(Visibility.PRIVATE);

        //when
        Project saved = projectRepository.saveAndFlush(project);
        Project savedProject = projectRepository.findById(saved.getId()).get();

        //then
        assertThat(savedProject.getId()).isNotNull();
        assertThat(savedProject.getColor().getHexCode()).isEqualTo("FF0000");
    }
}

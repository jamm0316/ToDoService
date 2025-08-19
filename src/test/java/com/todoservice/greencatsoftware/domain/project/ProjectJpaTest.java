package com.todoservice.greencatsoftware.domain.project;

import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.infrastructure.persistence.SpringDataColorRepository;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.infrastructure.persistence.SpringDataProjectJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ProjectJpaTest {
    @Autowired
    SpringDataProjectJpaRepository projectRepository;

    @Autowired
    SpringDataColorRepository colorRepository;

    private Color saveColor() {
        Color color = Color.create("RED", "FF0000");
        return colorRepository.saveAndFlush(color);
    }

    @Test
    @DisplayName("프로젝트 저장 조회")
    public void 프로젝트_저장_조회() throws Exception {
        //given
        Color color = saveColor();

        Project project = Project.create(
                color,
                "프로젝트A",
                Status.SCHEDULE,
                "프로젝트A 입니다.",
                true,
                Visibility.PRIVATE);

        //when
        Project saved = projectRepository.saveAndFlush(project);
        Project savedProject = projectRepository.findById(saved.getId()).get();

        //then
        assertThat(savedProject.getId()).isNotNull();
        assertThat(savedProject.getColor().getHexCode()).isEqualTo("FF0000");
    }

//    @Test
//    @DisplayName("필수값 누락 시 제약발생")
//    public void 필수값_누락시_제약발생() throws Exception {
//        //given
//        Project project = new Project();
//
//        //then
//        assertThatThrownBy(() -> projectRepository.saveAndFlush(project))
//                .isInstanceOf(ConstraintViolationException.class);
//    }
}

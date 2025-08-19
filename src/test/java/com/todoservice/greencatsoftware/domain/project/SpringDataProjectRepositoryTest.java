package com.todoservice.greencatsoftware.domain.project;

import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.infrastructure.persistence.SpringDataColorRepository;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.infrastructure.persistence.SpringDataProjectJpaRepository;
import jakarta.persistence.EntityManager;
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
public class SpringDataProjectRepositoryTest {
    @Autowired
    SpringDataProjectJpaRepository projectRepository;

    @Autowired
    SpringDataColorRepository colorRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("저장 & 조회")
    public void save_and_find() throws Exception {
        //given
        Color color = colorRepository.saveAndFlush(Color.create("RED", "#FF0000"));
        Project project = Project.create(
                color,
                "프로젝트A",
                Status.SCHEDULE,
                "프로젝트A 입니다.",
                true,
                Visibility.PRIVATE);

        //when
        Project saved = projectRepository.saveAndFlush(project);
        em.clear();
        Project found = projectRepository.findById(saved.getId()).orElseThrow();

        //then
        assertThat(found.getId()).isNotNull();
        assertThat(found.getColor().getName()).isEqualTo("RED");
        assertThat(found.getColor().getHexCode()).isEqualTo("#FF0000");
        assertThat(found.getName()).isEqualTo("프로젝트A");
        assertThat(found.getStatus()).isEqualTo(Status.SCHEDULE);
        assertThat(found.getDescription()).isEqualTo("프로젝트A 입니다.");
        assertThat(found.getIsPublic()).isTrue();
        assertThat(found.getVisibility()).isEqualTo(Visibility.PRIVATE);
    }

}

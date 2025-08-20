package com.todoservice.greencatsoftware.domain.task;

import com.todoservice.greencatsoftware.common.enums.DayLabel;
import com.todoservice.greencatsoftware.common.enums.Priority;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.infrastructure.persistence.SpringDataColorRepository;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.infrastructure.persistence.SpringDataProjectJpaRepository;
import com.todoservice.greencatsoftware.domain.task.domain.entity.Task;
import com.todoservice.greencatsoftware.domain.task.infrastructure.persistence.SpringDataTaskJpaRepository;
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
public class SpringDataTaskRepository {
    @Autowired
    SpringDataTaskJpaRepository taskRepository;

    @Autowired
    SpringDataColorRepository colorRepository;

    @Autowired
    SpringDataProjectJpaRepository projectRepository;

    @Autowired
    EntityManager em;

    private Color saveColor(String name, String hexCode) {
        Color color = Color.create(name, hexCode);
        return colorRepository.saveAndFlush(color);
    }

    private Project saveProject(Color color) {
        Project project = Project.create(
                color,
                "프로젝트B",
                Status.SCHEDULE,
                "프로젝트 입니다",
                true,
                Visibility.PRIVATE);
        return projectRepository.saveAndFlush(project);
    }

    @Test
    @DisplayName("저장 & 조회(스케쥴없음)")
    public void 테스크_저장_조회_검증() throws Exception {
        //given
        Color color = saveColor("RED", "#FF0000");
        Project project = saveProject(color);

        Task task = Task.create(project, color,
                Priority.HIGH,
                "알고리즘 테스트 1문제 풀기",
                null,
                DayLabel.MORNING,
                Status.SCHEDULE);

        //when
        Task save = taskRepository.saveAndFlush(task);
        em.clear();

        Task found = taskRepository.findById(save.getId()).get();

        //then
        assertThat(found.getProject().getName()).isEqualTo("프로젝트B");
        assertThat(found.getProject().getStatus()).isEqualTo(Status.SCHEDULE);
        assertThat(found.getColor().getName()).isEqualTo("RED");
        assertThat(found.getColor().getHexCode()).isEqualTo("#FF0000");
        assertThat(found.getPriority()).isEqualTo(Priority.HIGH);
        assertThat(found.getTitle()).isEqualTo("알고리즘 테스트 1문제 풀기");
        assertThat(found.getDayLabel()).isEqualTo(DayLabel.MORNING);
        assertThat(found.getStatus()).isEqualTo(Status.SCHEDULE);
    }
}

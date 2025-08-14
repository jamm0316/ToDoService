package com.todoservice.greencatsoftware.domain.task;

import com.todoservice.greencatsoftware.common.enums.DayLabel;
import com.todoservice.greencatsoftware.common.enums.Priority;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.domain.color.Color;
import com.todoservice.greencatsoftware.domain.color.ColorRepository;
import com.todoservice.greencatsoftware.domain.project.ProjectRepository;
import com.todoservice.greencatsoftware.domain.project.entity.Project;
import com.todoservice.greencatsoftware.domain.task.entity.Task;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TaskRepositoryTest {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    ProjectRepository projectRepository;

    private Color saveColor(String name, String hexCode) {
        Color color = new Color();
        color.setName(name);
        color.setHexCode(hexCode);
        return colorRepository.saveAndFlush(color);
    }

    private Project saveProject(Color color) {
        Project project = new Project();
        project.setColor(color);
        project.setName("프로젝트B");
        project.setStatus(Status.SCHEDULE);
        project.setIsPublic(true);
        project.setVisibility(Visibility.PRIVATE);
        return projectRepository.saveAndFlush(project);
    }

    @Test
    @DisplayName("테스크 저장 조회 검증")
    public void 테스크_저장_조회_검증() throws Exception {
        //given
        Color color = saveColor("RED", "#FF0000");
        Project project = saveProject(color);
        Task task = new Task();
        task.setProject(project);
        task.setColor(color);
        task.setPriority(Priority.HIGH);
        task.setTitle("알고리즘 테스트 1문제 풀기");
        task.setDayLabel(DayLabel.MORNING);
        task.setStartDate(LocalDate.now());
        task.setStartTimeEnabled(false);
        task.setDueTimeEnabled(false);
        task.setStatus(Status.SCHEDULE);

        //when
        Task save = taskRepository.saveAndFlush(task);
        Task savedTask = taskRepository.findById(save.getId()).get();

        //then
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getColor().getHexCode()).isEqualTo("#FF0000");
        assertThat(savedTask.getPriority()).isEqualTo(Priority.HIGH);
    }

    @Test
    @DisplayName("필수값 누락시 제약 발생")
    public void 필수값_누락시_제약_발생() throws Exception {
        //given
        Task task = new Task();

        //then
        assertThatThrownBy(() -> taskRepository.saveAndFlush(task))
                .isInstanceOf(ConstraintViolationException.class);
    }
}

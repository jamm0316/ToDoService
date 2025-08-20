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
import com.todoservice.greencatsoftware.domain.task.domain.vo.Schedule;
import com.todoservice.greencatsoftware.domain.task.infrastructure.persistence.SpringDataTaskJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public void saveAndFindWithoutSchedule() throws Exception {
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

    @Test
    @DisplayName("저장 & 조회 (스케쥴 포함)")
    public void saveAndFindWithSchedule() throws Exception {
        //given
        Color color = saveColor("RED", "#FF0000");
        Project project = saveProject(color);
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDateTime startDateTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDate dueDate = LocalDate.of(2025, 12, 31);
        Schedule schedule = Schedule.of(startDate, startDateTime, true, dueDate, null, false);
        Task task = Task.createWithSchedule(project, color,
                Priority.HIGH,
                "알고리즘 테스트 1문제 풀기",
                null,
                DayLabel.MORNING,
                schedule,
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
        assertThat(found.getSchedule().startDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(found.getSchedule().startTime()).isEqualTo(LocalDateTime.of(2025, 1, 1, 10, 0));
        assertThat(found.getSchedule().startTimeEnabled()).isTrue();
        assertThat(found.getSchedule().dueDate()).isEqualTo(LocalDate.of(2025, 12, 31));
        assertThat(found.getSchedule().dueTime()).isNull();
        assertThat(found.getSchedule().dueTimeEnabled()).isFalse();
        assertThat(found.getStatus()).isEqualTo(Status.SCHEDULE);
    }
    
    @Test
    @DisplayName("수정(더티체킹): title/priority/status/dayLabel/schedule 변경")
    public void updateDirtyChecking() throws Exception {
        //given
        Color color = saveColor("RED", "#FF0000");
        Project project = saveProject(color);
        Task task = taskRepository.saveAndFlush(Task.create(project, color, Priority.HIGH,
                "새로운 할 일", null, DayLabel.MORNING, Status.SCHEDULE));

        Color newColor = saveColor("BLUE", "#0000FF");
        Project newProject = projectRepository.saveAndFlush(
                Project.create(
                    newColor,
                    "새로운 프로젝트",
                    Status.SCHEDULE,
                    "새로운 프로젝트 입니다",
                    true,
                    Visibility.TEAM));

        //when
        task.changeProject(newProject);
        task.changeColor(newColor);
        task.changePriority(Priority.MEDIUM);
        task.changeTitle("변경된 할 일");
        task.changeDescription("할 일이 변경 되었어요.");
        task.changeDayLabel(DayLabel.AFTERNOON);
        task.changeSchedule(Schedule.of(LocalDate.of(2025, 1, 1), null, false, null, null, false));
        task.changeStatus(Status.COMPLETED);
        taskRepository.flush();

        //then
        assertThat(task.getProject().getName()).isEqualTo("새로운 프로젝트");
        assertThat(task.getProject().getStatus()).isEqualTo(Status.SCHEDULE);
        assertThat(task.getColor().getName()).isEqualTo("BLUE");
        assertThat(task.getColor().getHexCode()).isEqualTo("#0000FF");
        assertThat(task.getPriority()).isEqualTo(Priority.MEDIUM);
        assertThat(task.getTitle()).isEqualTo("변경된 할 일");
        assertThat(task.getDescription()).isEqualTo("할 일이 변경 되었어요.");
        assertThat(task.getDayLabel()).isEqualTo(DayLabel.AFTERNOON);
        assertThat(task.getSchedule().startDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(task.getSchedule().startTime()).isNull();
        assertThat(task.getSchedule().startTimeEnabled()).isFalse();
        assertThat(task.getSchedule().dueTime()).isNull();
        assertThat(task.getSchedule().dueTimeEnabled()).isFalse();
        assertThat(task.getStatus()).isEqualTo(Status.COMPLETED);
    }
}

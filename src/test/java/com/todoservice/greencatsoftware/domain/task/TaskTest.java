package com.todoservice.greencatsoftware.domain.task;

import com.todoservice.greencatsoftware.common.enums.DayLabel;
import com.todoservice.greencatsoftware.common.enums.Priority;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.domain.vo.Period;
import com.todoservice.greencatsoftware.domain.task.domain.entity.Task;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class TaskTest {

    private Color color(String name, String hexCode) {
        return Color.create(name, hexCode);
    }

    private Project projectWithPeriod() {
        return Project.createWithPeriod(color("RED", "#FF0000"), "프로젝트", Status.SCHEDULE,
                LocalDate.of(2005, 1, 1), LocalDate.of(2005, 12, 31), LocalDate.of(2005, 12, 31),
                "description", true, Visibility.PUBLIC);
    }

    @Test
    @DisplayName("정상 생성: 스케쥴 포함")
    public void createWithoutScheduleOk() throws Exception {
        //given
        Task task = Task.create(projectWithPeriod(), color("BLUE", "#0000FF"), Priority.HIGH, "해야할 일",
                "이러저렇게 한다", DayLabel.MORNING, Status.SCHEDULE);

        //then
        assertThat(task.getColor().getName()).isEqualTo("BLUE");
        assertThat(task.getColor().getHexCode()).isEqualTo("#0000FF");
        assertThat(task.getPriority()).isEqualTo(Priority.HIGH);
        assertThat(task.getTitle()).isEqualTo("해야할 일");
        assertThat(task.getDescription()).isEqualTo("이러저렇게 한다");
        assertThat(task.getDayLabel()).isEqualTo(DayLabel.MORNING);
        assertThat(task.getStatus()).isEqualTo(Status.SCHEDULE);
    }
}

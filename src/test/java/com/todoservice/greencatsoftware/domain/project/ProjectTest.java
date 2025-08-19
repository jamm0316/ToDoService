package com.todoservice.greencatsoftware.domain.project;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.domain.vo.Period;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProjectTest {

    @Test
    @DisplayName("정상 생성: 기간 없이 생성")
    public void create_without_period_ok() throws Exception {
        //given
        Color color = Color.create("RED", "FF0000");

        //when
        Project project = Project.create(color
                , "   나의 프로젝트    "
                , Status.SCHEDULE,
                "   잘해보자구~"
                , true
                , Visibility.PUBLIC);

        //then
        assertThat(project.getName()).isEqualTo("나의 프로젝트");
        assertThat(project.getStatus()).isEqualTo(Status.SCHEDULE);
        assertThat(project.getDescription()).isEqualTo("잘해보자구~");
        assertThat(project.getIsPublic()).isTrue();
        assertThat(project.getVisibility()).isEqualTo(Visibility.PUBLIC);
        assertThat(project.getColor().getHexCode()).isEqualTo("FF0000");
        assertThat(project.getColor().getName()).isEqualTo("RED");
    }
    
    @Test
    @DisplayName("저장 생성: 기간 포함 생성")
    public void create_with_period_ok() throws Exception {
        //given
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        Color color = Color.create("RED", "FF0000");
        Period period = Period.of(startDate, endDate, null);

        //when
        Project project = Project.createWithPeriod(
                color
                , "   나의 프로젝트    "
                , Status.SCHEDULE
                ,period.startDate()
                ,period.endDate()
                ,period.actualEndDate()
                ,"   잘해보자구~"
                , true
                , Visibility.PUBLIC);

        //then
        assertThat(project.getName()).isEqualTo("나의 프로젝트");
        assertThat(project.getStatus()).isEqualTo(Status.SCHEDULE);
        assertThat(project.getPeriod().startDate()).isEqualTo(startDate);
        assertThat(project.getPeriod().endDate()).isEqualTo(endDate);
        assertThat(project.getPeriod().actualEndDate()).isNull();
        assertThat(project.getDescription()).isEqualTo("잘해보자구~");
        assertThat(project.getIsPublic()).isTrue();
        assertThat(project.getVisibility()).isEqualTo(Visibility.PUBLIC);
        assertThat(project.getColor().getHexCode()).isEqualTo("FF0000");
        assertThat(project.getColor().getName()).isEqualTo("RED");
    }

    @Test
    @DisplayName("검증 실패: color, name, status, isPublic, visibility가 null 이면 BaseException 반환")
    public void create_fail_validation() throws Exception {
        //given
        Color color = Color.create("RED", "FF0000");

        //then
        assertThatThrownBy(() -> Project.create(
                null, "name", Status.SCHEDULE, "description", true, Visibility.PUBLIC
        )).isInstanceOf(BaseException.class).hasMessage(BaseResponseStatus.MISSING_COLOR_FOR_PROJECT.getMessage());

        assertThatThrownBy(() -> Project.create(
                color, "", Status.SCHEDULE, "description", true, Visibility.PUBLIC
        )).isInstanceOf(BaseException.class).hasMessage(BaseResponseStatus.MISSING_TITLE_FOR_PROJECT.getMessage());
    }
}

package com.todoservice.greencatsoftware.domain.project;

import com.todoservice.greencatsoftware.common.enums.Status;
import com.todoservice.greencatsoftware.common.enums.Visibility;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}

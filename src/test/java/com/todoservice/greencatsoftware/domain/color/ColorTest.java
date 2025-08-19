package com.todoservice.greencatsoftware.domain.color;

import com.todoservice.greencatsoftware.domain.color.entity.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ColorTest {
    @Test
    @DisplayName("생성 성공: trim 적용 및 Uppercase 적용")
    public void create_ok() throws Exception {
        //given
        Color color = Color.create("   reD    ", "    #FF0000    ");

        //then
        assertThat(color.getName()).isEqualTo("RED");
        assertThat(color.getHexCode()).isEqualTo("#FF0000");
    }
}

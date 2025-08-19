package com.todoservice.greencatsoftware.domain.color;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("생성 실패: name이 비면 BaseException 발생")
    public void creat_fail_empty_name() throws Exception {
        //then
        assertThatThrownBy(() -> Color.create("", "#FF0000"))
                .isInstanceOf(BaseException.class)
                .hasMessage(BaseResponseStatus.MISSING_NAME_FOR_COLOR.getMessage());
    }

    @Test
    @DisplayName("생성 실패: hexCode가 비면 BaseException 발생")
    public void create_fail_empty_hex() throws Exception {
        //then
        assertThatThrownBy(() -> Color.create("RED", ""))
                .isInstanceOf(BaseException.class)
                .hasMessage(BaseResponseStatus.MISSING_HEX_CODE_FOR_COLOR.getMessage());
    }
}

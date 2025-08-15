package com.todoservice.greencatsoftware.domain.color;

import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.model.ColorRepository;
import com.todoservice.greencatsoftware.domain.color.model.ColorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ColorServiceTest {

    @Mock
    private ColorRepository colorRepository;

    @InjectMocks
    private ColorService colorService;

    @Test
    @DisplayName("모든 색상 목록을 반환한다.")
    public void 모든_색상_목록_반환() throws Exception {
        //given
        Color color1 = new Color();
        color1.setName("RED");
        color1.setHexCode("#FF0000");

        Color color2 = new Color();
        color2.setName("GREEN");
        color2.setHexCode("#00FF00");
        when(colorRepository.findAll()).thenReturn(Arrays.asList(color1, color2));

        //when
        List<Color> colors = colorService.listColor();

        //then
        assertThat(colors).hasSize(2);
        assertThat(colors.get(0).getName()).isEqualTo("RED");
        assertThat(colors.get(1).getName()).isEqualTo("GREEN");
    }
    
    @Test
    @DisplayName("색상이 존재하면 해당 색상을 반환한다.")
    public void 색상_단건_조회() throws Exception {
        //given
        Color color = new Color();
        color.setName("RED");
        color.setHexCode("#FF0000");
        when(colorRepository.findById(color.getId())).thenReturn(Optional.of(color));

        //when
        Color findedColor = colorService.getColorByIdOrThrow(color.getId());

        //then
        assertThat(findedColor.getName()).isEqualTo("RED");
        assertThat(findedColor.getHexCode()).isEqualTo("#FF0000");
    }

    @Test
    @DisplayName("색상이 존재하지 않으면 BaseException 반환")
    public void 색상없으면_BaseException반환() throws Exception {
        //given
        when(colorRepository.findById(99L)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> colorService.getColorByIdOrThrow(99L))
                .isInstanceOf(BaseException.class)
                .hasMessage("요청 데이터가 유효하지 않습니다.");
    }

}

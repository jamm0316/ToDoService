package com.todoservice.greencatsoftware.domain.color;

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

import static org.assertj.core.api.Assertions.assertThat;
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
}

package com.todoservice.greencatsoftware.domain.color;

import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.infrastructure.persistence.SpringDataColorRepository;
import com.todoservice.greencatsoftware.domain.color.port.ColorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ColorJpaTest {
    @Autowired
    SpringDataColorRepository colorRepository;

    @Test
    @DisplayName("색상저장조회")
    public void 색상_저장_조회() throws Exception {
        //given
        Color color = Color.create("RED", "#FF0000");

        //when
        Color save = colorRepository.save(color);
        Color savedColor = colorRepository.findById(save.getId()).get();

        //then
        assertThat(savedColor.getId()).isNotNull();
        assertThat(savedColor.getHexCode()).isEqualTo("#FF0000");
    }

    @Test
    @DisplayName("색상명_유니크_제약")
    public void 색상명_유니크_제약() throws Exception {
        //given
        Color color1 = Color.create("RED", "#FF0000");
        Color color2 = Color.create("RED", "#0000FF");

        //when
        colorRepository.saveAndFlush(color1);

        //then
        assertThatThrownBy(() -> colorRepository.saveAndFlush(color2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("색상코드_유니크_제약")
    public void 생상코드_유니크_제약() throws Exception {
        //given
        Color color1 = Color.create("RED", "#FF0000");
        Color color2 = Color.create("BLUE", "#FF0000");

        //when
        colorRepository.saveAndFlush(color1);

        //then
        assertThatThrownBy(() -> colorRepository.saveAndFlush(color2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}

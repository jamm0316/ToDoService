package com.todoservice.greencatsoftware.domain;

import com.todoservice.greencatsoftware.domain.color.Color;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ColorJpaTest {
    @Autowired
    ColorRepository colorRepository;

    @Test
    @DisplayName("색상저장조회")
    public void 색상_저장_조회() throws Exception {
        //given
        Color color = new Color();
        color.setName("RED");
        color.setHexCode("#FF0000");

        //when
        Color save = colorRepository.save(color);
        Color savedColor = colorRepository.findById(save.getId()).get();

        //then
        assertThat(savedColor.getId()).isNotNull();
        assertThat(savedColor.getHexCode()).isEqualTo("#FF0000");
    }
}

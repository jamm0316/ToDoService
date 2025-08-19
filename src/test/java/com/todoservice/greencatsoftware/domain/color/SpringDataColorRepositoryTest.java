package com.todoservice.greencatsoftware.domain.color;

import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.infrastructure.persistence.SpringDataColorRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class SpringDataColorRepositoryTest {
    @Autowired
    SpringDataColorRepository colorRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("색상저장조회")
    public void 색상_저장_조회() throws Exception {
        //given
        Color color = Color.create("RED", "#FF0000");

        //when
        Color save = colorRepository.save(color);
        em.clear();

        Color savedColor = colorRepository.findById(save.getId()).orElseThrow();

        //then
        assertThat(savedColor.getId()).isNotNull();
        assertThat(savedColor.getName()).isEqualTo("RED");
        assertThat(savedColor.getHexCode()).isEqualTo("#FF0000");
    }
}

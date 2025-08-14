package com.todoservice.greencatsoftware.domain;

import com.todoservice.greencatsoftware.domain.color.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
}

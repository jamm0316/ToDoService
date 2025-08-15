package com.todoservice.greencatsoftware.domain.color.model;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponseStatus;
import com.todoservice.greencatsoftware.common.exception.BaseException;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorService {
    private final ColorRepository colorRepository;

    public List<Color> listColor() {
        return colorRepository.findAll();
    }

    public Color getColorByIdOrThrow(Long id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.VALIDATION_ERROR));
    }

    public Color createColor(String name, String hexCode) {
        Color color = new Color();
        color.setName(name);
        color.setHexCode(hexCode);
        return colorRepository.save(color);
    }

    public void deleteColor(Long id) {
        colorRepository.deleteById(id);
    }

    public Color updateColor(String name, String hexCode, Long id) {
        Color color = getColorByIdOrThrow(id);
        color.setName(name);
        color.setHexCode(hexCode);
        return color;
    }
}

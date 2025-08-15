package com.todoservice.greencatsoftware.domain.color;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponse;
import com.todoservice.greencatsoftware.domain.color.entity.Color;
import com.todoservice.greencatsoftware.domain.color.model.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/color")
public class ColorController {
    private final ColorService colorService;

    @GetMapping("")
    public BaseResponse<List<Color>> listColor() {
        return new BaseResponse<>(colorService.listColor());
    }

    @PostMapping("")
    public BaseResponse<Color> createColor(String name, String hexCode) {
        return new BaseResponse<>(colorService.createColor(name, hexCode));
    }

    @PatchMapping("/{id}")
    public BaseResponse<Color> updateColor(String name, String hexCode, @PathVariable Long id) {
        return new BaseResponse<>(colorService.updateColor(name, hexCode, id));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteColor(@PathVariable Long id) {
        colorService.deleteColor(id);
        return new BaseResponse<>();
    }
}
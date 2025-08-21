package com.todoservice.greencatsoftware.domain.project.presentation;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponse;
import com.todoservice.greencatsoftware.domain.project.presentation.dto.ProjectCreateRequest;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.application.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("")
    public BaseResponse<List<Project>> listProject() {
        return new BaseResponse<>(projectService.listProject());
    }

    @PostMapping("")
    public BaseResponse<Project> createColor(@Valid @RequestBody ProjectCreateRequest newProjectDTO) {
        return new BaseResponse<>(projectService.createProject(newProjectDTO));
    }

    @PatchMapping("/{id}")
    public BaseResponse<Project> updateColor(ProjectCreateRequest newProjectDTO, @PathVariable Long id) {
        return new BaseResponse<>(projectService.updateProject(newProjectDTO, id));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteColor(@PathVariable Long id) {
        projectService.deleteProjectById(id);
        return new BaseResponse<>();
    }
}

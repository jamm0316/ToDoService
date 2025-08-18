package com.todoservice.greencatsoftware.domain.project.presentation;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponse;
import com.todoservice.greencatsoftware.domain.project.presentation.dto.ProjectCreateRequest;
import com.todoservice.greencatsoftware.domain.project.domain.entity.Project;
import com.todoservice.greencatsoftware.domain.project.application.ProjectService;
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
    public BaseResponse<Project> createColor(ProjectCreateRequest newProjectDTO) {
        return new BaseResponse<>(projectService.createProject(newProjectDTO));
    }

    //todo: update로직 수정필요
    @PatchMapping("/{id}")
    public BaseResponse<Project> updateColor(ProjectCreateRequest newProjectDTO, @PathVariable Long id) {
        return new BaseResponse<>();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteColor(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new BaseResponse<>();
    }
}

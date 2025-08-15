package com.todoservice.greencatsoftware.domain.project;

import com.todoservice.greencatsoftware.common.baseResponse.BaseResponse;
import com.todoservice.greencatsoftware.domain.project.entity.Project;
import com.todoservice.greencatsoftware.domain.project.model.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

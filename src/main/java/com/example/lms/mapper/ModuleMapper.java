package com.example.lms.mapper;

import com.example.lms.dto.CreateModuleRequest;
import com.example.lms.dto.ModuleResponse;
import com.example.lms.dto.UpdateModuleRequest;
import com.example.lms.model.Course;
import com.example.lms.model.CourseModule;
import org.springframework.stereotype.Component;

@Component
public class ModuleMapper {

    public CourseModule toEntity(CreateModuleRequest request, Course course) {
        CourseModule module = new CourseModule();
        module.setCourse(course);
        module.setTitle(request.getTitle());
        module.setOrderIndex(request.getOrderIndex());
        return module;
    }

    public CourseModule toEntity(UpdateModuleRequest request, Course course, CourseModule existingModule) {
        existingModule.setCourse(course);
        existingModule.setTitle(request.getTitle());
        existingModule.setOrderIndex(request.getOrderIndex());
        return existingModule;
    }

    public ModuleResponse toResponse(CourseModule module) {
        ModuleResponse response = new ModuleResponse();
        response.setId(module.getId());
        response.setCourseId(module.getCourse().getId());
        response.setTitle(module.getTitle());
        response.setOrderIndex(module.getOrderIndex());
        return response;
    }
}

package com.example.lms.mapper;

import com.example.lms.dto.CreateLessonRequest;
import com.example.lms.dto.LessonResponse;
import com.example.lms.dto.UpdateLessonRequest;
import com.example.lms.model.CourseModule;
import com.example.lms.model.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    public Lesson toEntity(CreateLessonRequest request, CourseModule module) {
        Lesson lesson = new Lesson();
        lesson.setCourseModule(module);
        lesson.setTitle(request.getTitle());
        lesson.setContent(request.getContent());
        lesson.setVideoUrl(request.getVideoUrl());
        return lesson;
    }

    public Lesson toEntity(UpdateLessonRequest request, CourseModule module, Lesson existingLesson) {
        existingLesson.setCourseModule(module);
        existingLesson.setTitle(request.getTitle());
        existingLesson.setContent(request.getContent());
        existingLesson.setVideoUrl(request.getVideoUrl());
        return existingLesson;
    }

    public LessonResponse toResponse(Lesson lesson) {
        LessonResponse response = new LessonResponse();
        response.setId(lesson.getId());
        response.setModuleId(lesson.getCourseModule().getId());
        response.setTitle(lesson.getTitle());
        response.setContent(lesson.getContent());
        response.setVideoUrl(lesson.getVideoUrl());
        return response;
    }
}

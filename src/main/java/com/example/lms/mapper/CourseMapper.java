package com.example.lms.mapper;

import com.example.lms.dto.CourseResponse;
import com.example.lms.dto.CreateCourseRequest;
import com.example.lms.dto.UpdateCourseRequest;
import com.example.lms.model.Category;
import com.example.lms.model.Course;
import com.example.lms.model.User;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public Course toEntity(CreateCourseRequest request, Category category, User teacher) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCategory(category);
        course.setTeacher(teacher);
        return course;
    }

    public Course toEntity(UpdateCourseRequest request, Category category, User teacher, Course existingCourse) {
        existingCourse.setTitle(request.getTitle());
        existingCourse.setDescription(request.getDescription());
        existingCourse.setCategory(category);
        existingCourse.setTeacher(teacher);
        return existingCourse;
    }

    public CourseResponse toResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setDescription(course.getDescription());
        response.setCategoryId(course.getCategory().getId());
        response.setCategoryName(course.getCategory().getName());
        response.setTeacherId(course.getTeacher().getId());
        response.setTeacherName(course.getTeacher().getName());
        return response;
    }
}

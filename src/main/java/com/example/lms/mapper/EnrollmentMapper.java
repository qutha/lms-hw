package com.example.lms.mapper;

import com.example.lms.dto.CreateEnrollmentRequest;
import com.example.lms.dto.EnrollmentResponse;
import com.example.lms.dto.UpdateEnrollmentRequest;
import com.example.lms.model.Course;
import com.example.lms.model.Enrollment;
import com.example.lms.model.User;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    public Enrollment toEntity(CreateEnrollmentRequest request, User student, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(request.getStatus() != null ? request.getStatus() : "ACTIVE");
        return enrollment;
    }

    public Enrollment toEntity(UpdateEnrollmentRequest request, User student, Course course, Enrollment existingEnrollment) {
        existingEnrollment.setStudent(student);
        existingEnrollment.setCourse(course);
        existingEnrollment.setStatus(request.getStatus() != null ? request.getStatus() : "ACTIVE");
        return existingEnrollment;
    }

    public EnrollmentResponse toResponse(Enrollment enrollment) {
        EnrollmentResponse response = new EnrollmentResponse();
        response.setId(enrollment.getId());
        response.setStudentId(enrollment.getStudent().getId());
        response.setStudentName(enrollment.getStudent().getName());
        response.setCourseId(enrollment.getCourse().getId());
        response.setCourseTitle(enrollment.getCourse().getTitle());
        response.setEnrollDate(enrollment.getEnrollDate());
        response.setStatus(enrollment.getStatus());
        return response;
    }
}

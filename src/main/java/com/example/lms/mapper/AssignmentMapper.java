package com.example.lms.mapper;

import com.example.lms.dto.AssignmentResponse;
import com.example.lms.dto.CreateAssignmentRequest;
import com.example.lms.dto.UpdateAssignmentRequest;
import com.example.lms.model.Assignment;
import com.example.lms.model.Lesson;
import org.springframework.stereotype.Component;

@Component
public class AssignmentMapper {

    public Assignment toEntity(CreateAssignmentRequest request, Lesson lesson) {
        Assignment assignment = new Assignment();
        assignment.setLesson(lesson);
        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());
        assignment.setDueDate(request.getDueDate());
        assignment.setMaxScore(request.getMaxScore() != null ? request.getMaxScore() : 100);
        return assignment;
    }

    public Assignment toEntity(UpdateAssignmentRequest request, Lesson lesson, Assignment existingAssignment) {
        existingAssignment.setLesson(lesson);
        existingAssignment.setTitle(request.getTitle());
        existingAssignment.setDescription(request.getDescription());
        existingAssignment.setDueDate(request.getDueDate());
        existingAssignment.setMaxScore(request.getMaxScore() != null ? request.getMaxScore() : 100);
        return existingAssignment;
    }

    public AssignmentResponse toResponse(Assignment assignment) {
        AssignmentResponse response = new AssignmentResponse();
        response.setId(assignment.getId());
        response.setLessonId(assignment.getLesson().getId());
        response.setTitle(assignment.getTitle());
        response.setDescription(assignment.getDescription());
        response.setDueDate(assignment.getDueDate());
        response.setMaxScore(assignment.getMaxScore());
        return response;
    }
}

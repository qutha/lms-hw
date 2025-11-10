package com.example.lms.mapper;

import com.example.lms.dto.CreateSubmissionRequest;
import com.example.lms.dto.SubmissionResponse;
import com.example.lms.dto.UpdateSubmissionRequest;
import com.example.lms.model.Assignment;
import com.example.lms.model.Submission;
import com.example.lms.model.User;
import org.springframework.stereotype.Component;

@Component
public class SubmissionMapper {

    public Submission toEntity(CreateSubmissionRequest request, Assignment assignment, User student) {
        Submission submission = new Submission();
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setContent(request.getContent());
        submission.setScore(request.getScore());
        submission.setFeedback(request.getFeedback());
        return submission;
    }

    public Submission toEntity(UpdateSubmissionRequest request, Assignment assignment, User student, Submission existingSubmission) {
        existingSubmission.setAssignment(assignment);
        existingSubmission.setStudent(student);
        existingSubmission.setContent(request.getContent());
        existingSubmission.setScore(request.getScore());
        existingSubmission.setFeedback(request.getFeedback());
        return existingSubmission;
    }

    public SubmissionResponse toResponse(Submission submission) {
        SubmissionResponse response = new SubmissionResponse();
        response.setId(submission.getId());
        response.setAssignmentId(submission.getAssignment().getId());
        response.setAssignmentTitle(submission.getAssignment().getTitle());
        response.setStudentId(submission.getStudent().getId());
        response.setStudentName(submission.getStudent().getName());
        response.setSubmittedAt(submission.getSubmittedAt());
        response.setContent(submission.getContent());
        response.setScore(submission.getScore());
        response.setFeedback(submission.getFeedback());
        return response;
    }
}

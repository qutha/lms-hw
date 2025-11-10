package com.example.lms.mapper;

import com.example.lms.dto.CreateQuizSubmissionRequest;
import com.example.lms.dto.QuizSubmissionResponse;
import com.example.lms.dto.UpdateQuizSubmissionRequest;
import com.example.lms.model.Quiz;
import com.example.lms.model.QuizSubmission;
import com.example.lms.model.User;
import org.springframework.stereotype.Component;

@Component
public class QuizSubmissionMapper {

    public QuizSubmission toEntity(CreateQuizSubmissionRequest request, Quiz quiz, User student) {
        QuizSubmission submission = new QuizSubmission();
        submission.setQuiz(quiz);
        submission.setStudent(student);
        submission.setScore(request.getScore());
        return submission;
    }

    public QuizSubmission toEntity(UpdateQuizSubmissionRequest request, Quiz quiz, User student, QuizSubmission existingSubmission) {
        existingSubmission.setQuiz(quiz);
        existingSubmission.setStudent(student);
        existingSubmission.setScore(request.getScore());
        return existingSubmission;
    }

    public QuizSubmissionResponse toResponse(QuizSubmission submission) {
        QuizSubmissionResponse response = new QuizSubmissionResponse();
        response.setId(submission.getId());
        response.setQuizId(submission.getQuiz().getId());
        response.setQuizTitle(submission.getQuiz().getTitle());
        response.setStudentId(submission.getStudent().getId());
        response.setStudentName(submission.getStudent().getName());
        response.setScore(submission.getScore());
        response.setTakenAt(submission.getTakenAt());
        return response;
    }
}

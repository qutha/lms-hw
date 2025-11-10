package com.example.lms.mapper;

import com.example.lms.dto.CreateQuizRequest;
import com.example.lms.dto.QuizResponse;
import com.example.lms.dto.UpdateQuizRequest;
import com.example.lms.model.CourseModule;
import com.example.lms.model.Quiz;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {

    public Quiz toEntity(CreateQuizRequest request, CourseModule module) {
        Quiz quiz = new Quiz();
        quiz.setCourseModule(module);
        quiz.setTitle(request.getTitle());
        return quiz;
    }

    public Quiz toEntity(UpdateQuizRequest request, CourseModule module, Quiz existingQuiz) {
        existingQuiz.setCourseModule(module);
        existingQuiz.setTitle(request.getTitle());
        return existingQuiz;
    }

    public QuizResponse toResponse(Quiz quiz) {
        QuizResponse response = new QuizResponse();
        response.setId(quiz.getId());
        response.setModuleId(quiz.getCourseModule().getId());
        response.setTitle(quiz.getTitle());
        return response;
    }
}

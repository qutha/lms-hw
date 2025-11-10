package com.example.lms.mapper;

import com.example.lms.dto.CreateQuestionRequest;
import com.example.lms.dto.QuestionResponse;
import com.example.lms.dto.UpdateQuestionRequest;
import com.example.lms.model.Question;
import com.example.lms.model.Quiz;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public Question toEntity(CreateQuestionRequest request, Quiz quiz) {
        Question question = new Question();
        question.setQuiz(quiz);
        question.setText(request.getText());
        question.setType(request.getType());
        return question;
    }

    public Question toEntity(UpdateQuestionRequest request, Quiz quiz, Question existingQuestion) {
        existingQuestion.setQuiz(quiz);
        existingQuestion.setText(request.getText());
        existingQuestion.setType(request.getType());
        return existingQuestion;
    }

    public QuestionResponse toResponse(Question question) {
        QuestionResponse response = new QuestionResponse();
        response.setId(question.getId());
        response.setQuizId(question.getQuiz().getId());
        response.setText(question.getText());
        response.setType(question.getType());
        return response;
    }
}

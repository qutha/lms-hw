package com.example.lms.mapper;

import com.example.lms.dto.AnswerOptionResponse;
import com.example.lms.dto.CreateAnswerOptionRequest;
import com.example.lms.dto.UpdateAnswerOptionRequest;
import com.example.lms.model.AnswerOption;
import com.example.lms.model.Question;
import org.springframework.stereotype.Component;

@Component
public class AnswerOptionMapper {

    public AnswerOption toEntity(CreateAnswerOptionRequest request, Question question) {
        AnswerOption answerOption = new AnswerOption();
        answerOption.setQuestion(question);
        answerOption.setText(request.getText());
        answerOption.setCorrect(request.getIsCorrect());
        return answerOption;
    }

    public AnswerOption toEntity(UpdateAnswerOptionRequest request, Question question, AnswerOption existingAnswerOption) {
        existingAnswerOption.setQuestion(question);
        existingAnswerOption.setText(request.getText());
        existingAnswerOption.setCorrect(request.getIsCorrect());
        return existingAnswerOption;
    }

    public AnswerOptionResponse toResponse(AnswerOption answerOption) {
        AnswerOptionResponse response = new AnswerOptionResponse();
        response.setId(answerOption.getId());
        response.setQuestionId(answerOption.getQuestion().getId());
        response.setText(answerOption.getText());
        response.setIsCorrect(answerOption.isCorrect());
        return response;
    }
}

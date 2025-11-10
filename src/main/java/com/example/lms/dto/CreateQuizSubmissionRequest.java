package com.example.lms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateQuizSubmissionRequest {
    
    @NotNull(message = "ID теста не может быть пустым")
    private Long quizId;
    
    @NotNull(message = "ID студента не может быть пустым")
    private Long studentId;
    
    @PositiveOrZero(message = "Балл должен быть положительным числом или нулем")
    private Integer score;
}

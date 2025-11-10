package com.example.lms.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateQuizSubmissionRequest {
    @PositiveOrZero(message = "Балл должен быть положительным числом или нулем")
    private Integer score;
}

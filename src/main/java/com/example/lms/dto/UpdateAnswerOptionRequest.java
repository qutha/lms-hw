package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAnswerOptionRequest {
    @NotBlank(message = "Текст ответа не может быть пустым")
    private String text;

    @NotNull(message = "Поле isCorrect обязательно")
    private Boolean isCorrect;
}

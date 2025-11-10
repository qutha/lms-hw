package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateQuestionRequest {

    @NotNull(message = "ID теста не может быть пустым")
    private Long quizId;

    @NotBlank(message = "Текст вопроса не может быть пустым")
    @Size(max = 4000, message = "Текст вопроса не может превышать 4000 символов")
    private String text;

    @NotBlank(message = "Тип вопроса не может быть пустым")
    @Pattern(regexp = "SINGLE_CHOICE|MULTIPLE_CHOICE|TEXT",
            message = "Тип вопроса должен быть SINGLE_CHOICE, MULTIPLE_CHOICE или TEXT")
    private String type;
}

package com.example.lms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateSubmissionRequest {
    
    @NotNull(message = "ID задания не может быть пустым")
    private Long assignmentId;
    
    @NotNull(message = "ID студента не может быть пустым")
    private Long studentId;
    
    @Size(max = 10000, message = "Содержание ответа не может превышать 10000 символов")
    private String content;
    
    @PositiveOrZero(message = "Балл должен быть положительным числом или нулем")
    private Integer score;
    
    @Size(max = 2000, message = "Обратная связь не может превышать 2000 символов")
    private String feedback;
}

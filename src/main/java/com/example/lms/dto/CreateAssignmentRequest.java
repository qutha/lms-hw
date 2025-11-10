package com.example.lms.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;

@Data
public class CreateAssignmentRequest {
    
    @NotNull(message = "ID урока не может быть пустым")
    private Long lessonId;
    
    @NotBlank(message = "Название задания не может быть пустым")
    @Size(max = 255, message = "Название задания не может превышать 255 символов")
    private String title;
    
    @Size(max = 8000, message = "Описание задания не может превышать 8000 символов")
    private String description;
    
    @FutureOrPresent(message = "Срок сдачи должен быть в будущем или настоящем")
    private Instant dueDate;
    
    @PositiveOrZero(message = "Максимальный балл должен быть положительным числом или нулем")
    private Integer maxScore;
}

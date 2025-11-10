package com.example.lms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCourseReviewRequest {
    
    @NotNull(message = "ID курса не может быть пустым")
    private Long courseId;
    
    @NotNull(message = "ID студента не может быть пустым")
    private Long studentId;
    
    @NotNull(message = "Рейтинг обязателен")
    @Min(value = 1, message = "Рейтинг должен быть не менее 1")
    @Max(value = 5, message = "Рейтинг должен быть не более 5")
    private Integer rating;
    
    @Size(max = 4000, message = "Комментарий не может превышать 4000 символов")
    private String comment;
}

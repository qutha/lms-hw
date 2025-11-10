package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCourseRequest {
    
    @NotBlank(message = "Название курса не может быть пустым")
    @Size(max = 255, message = "Название курса не может превышать 255 символов")
    private String title;
    
    @Size(max = 4000, message = "Описание курса не может превышать 4000 символов")
    private String description;
    
    @NotNull(message = "ID категории не может быть пустым")
    private Long categoryId;
    
    @NotNull(message = "ID преподавателя не может быть пустым")
    private Long teacherId;
}

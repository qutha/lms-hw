package com.example.lms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateQuizRequest {
    
    @NotNull(message = "ID модуля не может быть пустым")
    private Long moduleId;
    
    @Size(max = 255, message = "Название теста не может превышать 255 символов")
    private String title;
}

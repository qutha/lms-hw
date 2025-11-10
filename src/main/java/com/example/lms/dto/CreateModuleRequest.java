package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateModuleRequest {
    
    @NotNull(message = "ID курса не может быть пустым")
    private Long courseId;
    
    @NotBlank(message = "Название модуля не может быть пустым")
    @Size(max = 255, message = "Название модуля не может превышать 255 символов")
    private String title;
    
    @PositiveOrZero(message = "Порядковый индекс должен быть положительным числом или нулем")
    private Integer orderIndex;
}

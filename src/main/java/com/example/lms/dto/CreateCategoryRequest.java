package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryRequest {
    
    @NotBlank(message = "Название категории не может быть пустым")
    @Size(max = 255, message = "Название категории не может превышать 255 символов")
    private String name;
}

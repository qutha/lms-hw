package com.example.lms.dto;

import com.example.lms.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
    
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(max = 30, message = "Имя пользователя не может превышать 30 символов")
    private String name;
    
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен быть корректным адресом")
    @Size(max = 255, message = "Email не может превышать 255 символов")
    private String email;
    
    @NotNull(message = "Роль пользователя обязательна")
    private UserRole role;
}

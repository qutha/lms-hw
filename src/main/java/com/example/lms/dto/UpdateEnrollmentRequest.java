package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateEnrollmentRequest {
    
    @NotNull(message = "ID студента не может быть пустым")
    private Long studentId;
    
    @NotNull(message = "ID курса не может быть пустым")
    private Long courseId;
    
    @NotBlank(message = "Статус записи не может быть пустым")
    @Pattern(regexp = "ACTIVE|INACTIVE|COMPLETED", message = "Статус должен быть ACTIVE, INACTIVE или COMPLETED")
    private String status;
}

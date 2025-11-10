package com.example.lms.dto;

import com.example.lms.model.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    
    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

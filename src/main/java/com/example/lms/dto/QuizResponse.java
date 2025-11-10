package com.example.lms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuizResponse {
    
    private Long id;
    private Long moduleId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

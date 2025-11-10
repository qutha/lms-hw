package com.example.lms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerOptionResponse {
    
    private Long id;
    private Long questionId;
    private String text;
    private Boolean isCorrect;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

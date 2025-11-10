package com.example.lms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionResponse {
    private Long id;
    private Long quizId;
    private String text;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

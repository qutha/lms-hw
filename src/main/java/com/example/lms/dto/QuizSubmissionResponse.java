package com.example.lms.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class QuizSubmissionResponse {
    
    private Long id;
    private Long quizId;
    private String quizTitle;
    private Long studentId;
    private String studentName;
    private Integer score;
    private Instant takenAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

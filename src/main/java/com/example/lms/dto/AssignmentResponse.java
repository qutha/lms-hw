package com.example.lms.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class AssignmentResponse {
    
    private Long id;
    private Long lessonId;
    private String title;
    private String description;
    private Instant dueDate;
    private Integer maxScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

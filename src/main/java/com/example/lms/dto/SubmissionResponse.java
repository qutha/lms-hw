package com.example.lms.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class SubmissionResponse {
    
    private Long id;
    private Long assignmentId;
    private String assignmentTitle;
    private Long studentId;
    private String studentName;
    private Instant submittedAt;
    private String content;
    private Integer score;
    private String feedback;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

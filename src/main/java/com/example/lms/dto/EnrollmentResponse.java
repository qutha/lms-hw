package com.example.lms.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class EnrollmentResponse {
    
    private Long id;
    private Long studentId;
    private String studentName;
    private Long courseId;
    private String courseTitle;
    private Instant enrollDate;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

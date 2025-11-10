package com.example.lms.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class CourseReviewResponse {
    
    private Long id;
    private Long courseId;
    private String courseTitle;
    private Long studentId;
    private String studentName;
    private Integer rating;
    private String comment;
    private Instant createdAtReview;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

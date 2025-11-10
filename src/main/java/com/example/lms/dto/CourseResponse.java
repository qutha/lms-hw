package com.example.lms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseResponse {
    
    private Long id;
    private String title;
    private String description;
    private Long categoryId;
    private String categoryName;
    private Long teacherId;
    private String teacherName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

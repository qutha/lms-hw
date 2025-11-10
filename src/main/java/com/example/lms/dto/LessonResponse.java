package com.example.lms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonResponse {
    
    private Long id;
    private Long moduleId;
    private String title;
    private String content;
    private String videoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

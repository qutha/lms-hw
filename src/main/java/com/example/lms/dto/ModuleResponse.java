package com.example.lms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ModuleResponse {
    
    private Long id;
    private Long courseId;
    private String title;
    private Integer orderIndex;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.example.lms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponse {
    
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

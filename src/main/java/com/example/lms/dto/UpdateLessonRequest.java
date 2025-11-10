package com.example.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateLessonRequest {
    @NotBlank(message = "Название урока не может быть пустым")
    @Size(max = 255, message = "Название урока не может превышать 255 символов")
    private String title;

    @Size(max = 10000, message = "Содержание урока не может превышать 10000 символов")
    private String content;

    @Size(max = 500, message = "URL видео не может превышать 500 символов")
    private String videoUrl;
}

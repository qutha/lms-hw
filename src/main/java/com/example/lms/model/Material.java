package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "materials")
@Getter
@Setter
public class Material extends ApplicationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Урок для материала обязателен")
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Тип ресурса обязателен")
    private ResourceType type;

    @Column(nullable = false)
    @NotBlank(message = "Название материала не может быть пустым")
    @Size(max = 255, message = "Название материала не может превышать 255 символов")
    private String title;

    @Size(max = 500, message = "URL материала не может превышать 500 символов")
    private String url;

    @Size(max = 1000, message = "Описание материала не может превышать 1000 символов")
    private String description;
}
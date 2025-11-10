package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
public class Lesson extends ApplicationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Модуль урока обязателен")
    private CourseModule courseModule;

    @Column(nullable = false)
    @NotBlank(message = "Название урока не может быть пустым")
    @Size(max = 255, message = "Название урока не может превышать 255 символов")
    private String title;

    @Column(length = 10000)
    @Size(max = 10000, message = "Содержание урока не может превышать 10000 символов")
    private String content;

    @Size(max = 500, message = "URL видео не может превышать 500 символов")
    private String videoUrl;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Assignment> assignments = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Material> materials = new ArrayList<>();
}
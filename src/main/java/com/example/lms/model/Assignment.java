package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assignments")
@Getter
@Setter
public class Assignment extends ApplicationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Урок не может быть пустым")
    private Lesson lesson;

    @Column(nullable = false)
    @NotBlank(message = "Название задания не может быть пустым")
    @Size(max = 255, message = "Название задания не может превышать 255 символов")
    private String title;

    @Column(length = 8000)
    @Size(max = 8000, message = "Описание задания не может превышать 8000 символов")
    private String description;

    @FutureOrPresent(message = "Срок сдачи должен быть в будущем или настоящем")
    private Instant dueDate;

    @Column(name = "max_score")
    @PositiveOrZero(message = "Максимальный балл должен быть положительным числом или нулем")
    private Integer maxScore = 100;

    @OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Submission> submissions = new ArrayList<>();
}

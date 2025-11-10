package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "quiz_submissions")
@Getter
@Setter
public class QuizSubmission extends ApplicationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Тест для ответа обязателен")
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Студент для ответа обязателен")
    private User student;

    @PositiveOrZero(message = "Балл должен быть положительным числом или нулем")
    private Integer score;

    private Instant takenAt = Instant.now();
}
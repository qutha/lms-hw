package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "submissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"assignment_id", "student_id"}))
@Getter
@Setter
public class Submission extends ApplicationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Задание для ответа обязательно")
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Студент для ответа обязателен")
    private User student;

    private Instant submittedAt = Instant.now();

    @Column(length = 10000)
    @Size(max = 10000, message = "Содержание ответа не может превышать 10000 символов")
    private String content;

    @PositiveOrZero(message = "Балл должен быть положительным числом или нулем")
    private Integer score;

    @Size(max = 2000, message = "Обратная связь не может превышать 2000 символов")
    private String feedback;
}

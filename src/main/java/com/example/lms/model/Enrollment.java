package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
@Getter
@Setter
public class Enrollment extends ApplicationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Студент обязателен")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Курс обязателен")
    private Course course;

    @Column(name = "enrol_date")
    private Instant enrollDate = Instant.now();

    @Column(nullable = false)
    @NotBlank(message = "Статус записи не может быть пустым")
    @Pattern(regexp = "ACTIVE|INACTIVE|COMPLETED", message = "Статус должен быть ACTIVE, INACTIVE или COMPLETED")
    private String status = "ACTIVE";
}
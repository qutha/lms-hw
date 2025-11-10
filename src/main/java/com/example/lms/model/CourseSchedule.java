package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "course_schedules")
@Getter
@Setter
public class CourseSchedule extends ApplicationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Курс для расписания обязателен")
    private Course course;

    @FutureOrPresent(message = "Дата начала должна быть в будущем или настоящем")
    private LocalDate startDate;

    @FutureOrPresent(message = "Дата окончания должна быть в будущем или настоящем")
    private LocalDate endDate;

    @Size(max = 20, message = "День недели не может превышать 20 символов")
    private String weekday;
}

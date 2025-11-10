package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modules")
@Getter
@Setter
public class CourseModule extends ApplicationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Курс модуля обязателен")
    private Course course;

    @Column(nullable = false)
    @NotBlank(message = "Название модуля не может быть пустым")
    @Size(max = 255, message = "Название модуля не может превышать 255 символов")
    private String title;

    @Column(name = "order_index")
    @PositiveOrZero(message = "Порядковый индекс должен быть положительным числом или нулем")
    private Integer orderIndex;

    @OneToMany(mappedBy = "courseModule", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Lesson> lessons = new ArrayList<>();
}

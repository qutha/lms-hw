package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question extends ApplicationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Тест для вопроса обязателен")
    private Quiz quiz;

    @Column(nullable = false, length = 4000)
    @NotBlank(message = "Текст вопроса не может быть пустым")
    @Size(max = 4000, message = "Текст вопроса не может превышать 4000 символов")
    private String text;

    @Column(nullable = false)
    @NotBlank(message = "Тип вопроса не может быть пустым")
    @Pattern(regexp = "SINGLE_CHOICE|MULTIPLE_CHOICE|TEXT",
            message = "Тип вопроса должен быть SINGLE_CHOICE, MULTIPLE_CHOICE или TEXT")
    private String type;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<AnswerOption> options = new ArrayList<>();
}

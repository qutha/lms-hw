package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "answer_options")
@Getter
@Setter
public class AnswerOption extends ApplicationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Вопрос не может быть пустым")
    private Question question;

    @Column(nullable = false)
    @NotBlank(message = "Текст ответа не может быть пустым")
    private String text;

    @Column(nullable = false, name = "is_correct")
    @NotNull(message = "Поле isCorrect обязательно")
    private boolean isCorrect;
}

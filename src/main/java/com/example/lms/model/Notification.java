package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification extends ApplicationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Пользователь для уведомления обязателен")
    private User user;

    @Column(nullable = false)
    @NotBlank(message = "Сообщение уведомления не может быть пустым")
    @Size(max = 1000, message = "Сообщение уведомления не может превышать 1000 символов")
    private String message;

    private boolean read = false;
    private Instant createdAtNote = Instant.now();
}

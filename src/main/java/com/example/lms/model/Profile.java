package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profiles")
@Getter
@Setter
public class Profile extends ApplicationEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnore
    @NotNull(message = "Пользователь для профиля обязателен")
    private User user;

    @Size(max = 2000, message = "Биография не может превышать 2000 символов")
    private String bio;

    @Column(name = "avatar_url")
    @Size(max = 500, message = "URL аватара не может превышать 500 символов")
    private String avatarUrl;
}
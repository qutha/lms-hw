package com.example.lms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
public class User extends ApplicationEntity {

    @Column(nullable = false)
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(max = 30, message = "Имя пользователя не может превышать 30 символов")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен быть корректным адресом")
    @Size(max = 255, message = "Email не может превышать 255 символов")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Роль пользователя обязательна")
    private UserRole role;
}
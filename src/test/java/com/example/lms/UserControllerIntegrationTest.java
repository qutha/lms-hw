package com.example.lms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllUsers_ReturnsUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getUserById_ExistingId_ReturnsUser() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getUserById_User1_VerifyDetails() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Петр Иванов"))
                .andExpect(jsonPath("$.email").value("petr.ivanov@mail.ru"))
                .andExpect(jsonPath("$.role").value("TEACHER"));
    }

    @Test
    void getUserById_User4_VerifyStudentRole() throws Exception {
        mockMvc.perform(get("/api/users/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("Анастасия Федорова"))
                .andExpect(jsonPath("$.email").value("anastasia.fedorova@mail.ru"))
                .andExpect(jsonPath("$.role").value("STUDENT"));
    }

    @Test
    void getUserById_User9_VerifyAdminRole() throws Exception {
        mockMvc.perform(get("/api/users/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(9))
                .andExpect(jsonPath("$.name").value("Главный администратор"))
                .andExpect(jsonPath("$.email").value("admin@eduplatform.ru"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void getUserById_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUsers_VerifyMultipleUsersReturned() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(9));
    }
}
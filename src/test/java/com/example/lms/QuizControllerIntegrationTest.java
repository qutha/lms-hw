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
class QuizControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getQuizByModule_ExistingModule_ReturnsQuiz() throws Exception {
        mockMvc.perform(get("/api/quizzes/module/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getQuizByModule_Module1_VerifyQuizTitle() throws Exception {
        mockMvc.perform(get("/api/quizzes/module/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Проверка знаний основ Java"));
    }

    @Test
    void getQuizByModule_Module2_ReturnsQuiz() throws Exception {
        mockMvc.perform(get("/api/quizzes/module/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("Тестирование по объектно-ориентированному программированию"));
    }

    @Test
    void getQuizByModule_Module6_ReturnsQuiz() throws Exception {
        mockMvc.perform(get("/api/quizzes/module/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("Контрольный тест по SQL"));
    }

    @Test
    void getQuizByModule_NonExistingModule_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/quizzes/module/999"))
                .andExpect(status().isNotFound());
    }
}

package com.example.lms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class QuestionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllQuestions_ReturnsQuestions() throws Exception {
        mockMvc.perform(get("/api/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].text").exists());
    }

    @Test
    void getQuestionById_ExistingId_ReturnsQuestion() throws Exception {
        mockMvc.perform(get("/api/questions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("Какая команда применяется для печати текста в консоль Java?"))
                .andExpect(jsonPath("$.type").value("SINGLE_CHOICE"));
    }

    @Test
    void getQuestionById_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/questions/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getQuestionsByQuiz_ExistingQuiz_ReturnsQuestions() throws Exception {
        mockMvc.perform(get("/api/questions/quiz/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].text").exists())
                .andExpect(jsonPath("$[1].text").exists());
    }

    @Test
    void getQuestionsByQuiz_NonExistingQuiz_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/questions/quiz/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getQuestionsByQuiz_VerifyQuestionTypes() throws Exception {
        mockMvc.perform(get("/api/questions/quiz/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("SINGLE_CHOICE"))
                .andExpect(jsonPath("$[1].type").value("MULTIPLE_CHOICE"));
    }

    @Test
    void createQuestion_ValidData_ReturnsCreatedQuestion() throws Exception {
        String questionJson = """
                {
                    "quizId": 1,
                    "text": "Что такое JVM?",
                    "type": "SINGLE_CHOICE"
                }
                """;

        mockMvc.perform(post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Что такое JVM?"))
                .andExpect(jsonPath("$.type").value("SINGLE_CHOICE"));
    }

    @Test
    void updateQuestion_ExistingId_ReturnsUpdatedQuestion() throws Exception {
        String updatedJson = """
                {
                    "text": "Обновленный вопрос о Java?",
                    "type": "MULTIPLE_CHOICE"
                }
                """;

        mockMvc.perform(put("/api/questions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("Обновленный вопрос о Java?"))
                .andExpect(jsonPath("$.type").value("MULTIPLE_CHOICE"));
    }

    @Test
    void updateQuestion_NonExistingId_ReturnsNotFound() throws Exception {
        String updatedJson = """
                {
                    "text": "Вопрос",
                    "type": "SINGLE_CHOICE"
                }
                """;

        mockMvc.perform(put("/api/questions/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteQuestion_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/questions/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/questions/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteQuestion_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/questions/999"))
                .andExpect(status().isNotFound());
    }
}

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
class AnswerOptionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllAnswerOptions_ReturnsOptions() throws Exception {
        mockMvc.perform(get("/api/answer-options"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].text").exists());
    }

    @Test
    void getAnswerOptionById_ExistingId_ReturnsOption() throws Exception {
        mockMvc.perform(get("/api/answer-options/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("System.out.println()"))
                .andExpect(jsonPath("$.isCorrect").value("true"));
    }

    @Test
    void getAnswerOptionById_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/answer-options/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAnswerOptionsByQuestion_ExistingQuestion_ReturnsOptions() throws Exception {
        mockMvc.perform(get("/api/answer-options/question/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].text").exists())
                .andExpect(jsonPath("$[1].text").exists())
                .andExpect(jsonPath("$[2].text").exists());
    }

    @Test
    void getAnswerOptionsByQuestion_NonExistingQuestion_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/answer-options/question/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getAnswerOptionsByQuestion_VerifyCorrectAnswer() throws Exception {
        mockMvc.perform(get("/api/answer-options/question/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isCorrect").value("true"))
                .andExpect(jsonPath("$[1].isCorrect").value("false"))
                .andExpect(jsonPath("$[2].isCorrect").value("false"));
    }

    @Test
    void createAnswerOption_ValidData_ReturnsCreatedOption() throws Exception {
        String optionJson = """
                {
                    "questionId": 1,
                    "text": "Новый вариант ответа",
                    "isCorrect": false
                }
                """;

        mockMvc.perform(post("/api/answer-options")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(optionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Новый вариант ответа"))
                .andExpect(jsonPath("$.isCorrect").value("false"));
    }

    @Test
    void updateAnswerOption_ExistingId_ReturnsUpdatedOption() throws Exception {
        String updatedJson = """
                {
                    "text": "Обновленный вариант ответа",
                    "isCorrect": true
                }
                """;

        mockMvc.perform(put("/api/answer-options/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("Обновленный вариант ответа"))
                .andExpect(jsonPath("$.isCorrect").value("true"));
    }

    @Test
    void updateAnswerOption_NonExistingId_ReturnsNotFound() throws Exception {
        String updatedJson = """
                {
                    "text": "Вариант ответа",
                    "isCorrect": false
                }
                """;

        mockMvc.perform(put("/api/answer-options/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAnswerOption_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/answer-options/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/answer-options/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAnswerOption_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/answer-options/999"))
                .andExpect(status().isNotFound());
    }
}
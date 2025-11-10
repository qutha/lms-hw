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
class QuizSubmissionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllQuizSubmissions_ReturnsSubmissions() throws Exception {
        mockMvc.perform(get("/api/quiz-submissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].score").exists());
    }

    @Test
    void getQuizSubmissionById_ExistingId_ReturnsSubmission() throws Exception {
        mockMvc.perform(get("/api/quiz-submissions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.score").value(88));
    }

    @Test
    void getQuizSubmissionById_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/quiz-submissions/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getQuizSubmissionsByStudent_ExistingStudent_ReturnsSubmissions() throws Exception {
        mockMvc.perform(get("/api/quiz-submissions/student/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].score").exists());
    }

    @Test
    void getQuizSubmissionsByStudent_NonExistingStudent_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/quiz-submissions/student/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getQuizSubmissionsByQuiz_ExistingQuiz_ReturnsSubmissions() throws Exception {
        mockMvc.perform(get("/api/quiz-submissions/quiz/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].score").exists())
                .andExpect(jsonPath("$[1].score").exists());
    }

    @Test
    void getQuizSubmissionsByQuiz_NonExistingQuiz_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/quiz-submissions/quiz/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getQuizSubmissionByQuizAndStudent_ExistingSubmission_ReturnsSubmission() throws Exception {
        mockMvc.perform(get("/api/quiz-submissions/quiz/1/student/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(88));
    }

    @Test
    void getQuizSubmissionByQuizAndStudent_NonExistingSubmission_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/quiz-submissions/quiz/1/student/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getQuizSubmissionsByQuiz_VerifyScores() throws Exception {
        mockMvc.perform(get("/api/quiz-submissions/quiz/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].score").value(88))
                .andExpect(jsonPath("$[1].score").value(72));
    }

    @Test
    void createQuizSubmission_ValidData_ReturnsCreatedSubmission() throws Exception {
        String submissionJson = """
                {
                    "quiz": 1,
                    "student": 1,
                    "score": 95
                }
                """;

        mockMvc.perform(post("/api/quiz-submissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(submissionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(95));
    }

    @Test
    void updateQuizSubmission_ExistingId_ReturnsUpdatedSubmission() throws Exception {
        String updatedJson = """
                {
                    "score": 100
                }
                """;

        mockMvc.perform(put("/api/quiz-submissions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.score").value(100));
    }

    @Test
    void updateQuizSubmission_NonExistingId_ReturnsNotFound() throws Exception {
        String updatedJson = """
                {
                    "score": 80
                }
                """;

        mockMvc.perform(put("/api/quiz-submissions/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteQuizSubmission_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/quiz-submissions/1"))
                .andExpect(status().isNoContent());
        
        mockMvc.perform(get("/api/quiz-submissions/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteQuizSubmission_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/quiz-submissions/999"))
                .andExpect(status().isNotFound());
    }
}

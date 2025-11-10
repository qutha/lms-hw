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
class AssignmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllAssignments_ReturnsAssignments() throws Exception {
        mockMvc.perform(get("/api/assignments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").exists());
    }

    @Test
    void getAssignmentById_ExistingId_ReturnsAssignment() throws Exception {
        mockMvc.perform(get("/api/assignments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Стартовая программа"))
                .andExpect(jsonPath("$.maxScore").value(100));
    }

    @Test
    void getAssignmentById_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/assignments/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAssignment_ValidData_ReturnsCreatedAssignment() throws Exception {
        String assignmentJson = """
                {
                    "lessonId": 1,
                    "title": "Новое задание",
                    "description": "Описание нового задания",
                    "maxScore": 100
                }
                """;

        mockMvc.perform(post("/api/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(assignmentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Новое задание"))
                .andExpect(jsonPath("$.maxScore").value(100));
    }

    @Test
    void updateAssignment_ExistingId_ReturnsUpdatedAssignment() throws Exception {
        String updatedJson = """
                {
                    "title": "Обновленное задание",
                    "description": "Новое описание",
                    "maxScore": 150
                }
                """;

        mockMvc.perform(put("/api/assignments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Обновленное задание"))
                .andExpect(jsonPath("$.maxScore").value(150));
    }

    @Test
    void updateAssignment_NonExistingId_ReturnsNotFound() throws Exception {
        String updatedJson = """
                {
                    "title": "Обновленное задание",
                    "description": "Описание",
                    "maxScore": 100
                }
                """;

        mockMvc.perform(put("/api/assignments/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAssignment_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/assignments/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/assignments/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAssignment_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/assignments/999"))
                .andExpect(status().isNotFound());
    }
}

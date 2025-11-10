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
class LessonControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllLessons_ReturnsLessons() throws Exception {
        mockMvc.perform(get("/api/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").exists());
    }

    @Test
    void getLessonById_ExistingId_ReturnsLesson() throws Exception {
        mockMvc.perform(get("/api/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Написание первого приложения на Java"));
    }

    @Test
    void getLessonById_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/lessons/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getLessonsByModule_ExistingModule_ReturnsLessons() throws Exception {
        mockMvc.perform(get("/api/lessons/module/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").exists());
    }

    @Test
    void getLessonsByModule_NonExistingModule_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/lessons/module/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createLesson_ValidData_ReturnsCreatedLesson() throws Exception {
        String lessonJson = """
                {
                    "title": "Новый урок по Java",
                    "content": "Содержание урока",
                    "videoUrl": "/videos/new-lesson.mp4"
                }
                """;

        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lessonJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Новый урок по Java"))
                .andExpect(jsonPath("$.content").value("Содержание урока"));
    }

    @Test
    void updateLesson_ExistingId_ReturnsUpdatedLesson() throws Exception {
        String updatedJson = """
                {
                    "title": "Обновленный урок",
                    "content": "Обновленное содержание",
                    "videoUrl": "/videos/updated.mp4"
                }
                """;

        mockMvc.perform(put("/api/lessons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Обновленный урок"));
    }

    @Test
    void updateLesson_NonExistingId_ReturnsNotFound() throws Exception {
        String updatedJson = """
                {
                    "title": "Урок",
                    "content": "Содержание"
                }
                """;

        mockMvc.perform(put("/api/lessons/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteLesson_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/lessons/1"))
                .andExpect(status().isNoContent());
        
        mockMvc.perform(get("/api/lessons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteLesson_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/lessons/999"))
                .andExpect(status().isNotFound());
    }
}

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
class ModuleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllModules_ReturnsModules() throws Exception {
        mockMvc.perform(get("/api/modules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").exists());
    }

    @Test
    void getModuleById_ExistingId_ReturnsModule() throws Exception {
        mockMvc.perform(get("/api/modules/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Начало работы с Java"))
                .andExpect(jsonPath("$.orderIndex").value(1));
    }

    @Test
    void getModuleById_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/modules/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getModulesByCourse_ExistingCourse_ReturnsModules() throws Exception {
        mockMvc.perform(get("/api/modules/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[1].title").exists());
    }

    @Test
    void getModulesByCourse_NonExistingCourse_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/modules/course/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getModulesByCourse_VerifyOrderIndex() throws Exception {
        mockMvc.perform(get("/api/modules/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderIndex").value(1))
                .andExpect(jsonPath("$[1].orderIndex").value(2))
                .andExpect(jsonPath("$[2].orderIndex").value(3));
    }

    @Test
    void createModule_ValidData_ReturnsCreatedModule() throws Exception {
        String moduleJson = """
                {
                    "title": "Новый модуль",
                    "orderIndex": 4
                }
                """;

        mockMvc.perform(post("/api/modules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moduleJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Новый модуль"))
                .andExpect(jsonPath("$.orderIndex").value(4));
    }

    @Test
    void updateModule_ExistingId_ReturnsUpdatedModule() throws Exception {
        String updatedJson = """
                {
                    "title": "Обновленный модуль",
                    "orderIndex": 5
                }
                """;

        mockMvc.perform(put("/api/modules/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Обновленный модуль"))
                .andExpect(jsonPath("$.orderIndex").value(5));
    }

    @Test
    void updateModule_NonExistingId_ReturnsNotFound() throws Exception {
        String updatedJson = """
                {
                    "title": "Модуль",
                    "orderIndex": 1
                }
                """;

        mockMvc.perform(put("/api/modules/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteModule_ExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/modules/1"))
                .andExpect(status().isNoContent());
        
        mockMvc.perform(get("/api/modules/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteModule_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/modules/999"))
                .andExpect(status().isNotFound());
    }
}

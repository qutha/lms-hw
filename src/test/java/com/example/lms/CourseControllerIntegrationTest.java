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
class CourseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCoursesByTeacher_ExistingTeacher_ReturnsCourses() throws Exception {
        mockMvc.perform(get("/api/courses/teacher/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getCourseById_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/courses/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCoursesByTeacher_Teacher2_ReturnsCourses() throws Exception {
        mockMvc.perform(get("/api/courses/teacher/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getCoursesByTeacher_Teacher3_ReturnsCourses() throws Exception {
        mockMvc.perform(get("/api/courses/teacher/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Высшая математика для IT"));
    }

    @Test
    void getCourseById_Course1_VerifyDetails() throws Exception {
        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Основы Java программирования"))
                .andExpect(jsonPath("$.description").value("Комплексный курс для новичков по языку Java"));
    }

    @Test
    void getCourseById_Course3_VerifyDetails() throws Exception {
        mockMvc.perform(get("/api/courses/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("SQL и реляционные базы данных"));
    }

    @Test
    void getCoursesByTeacher_NonExistingTeacher_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/courses/teacher/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
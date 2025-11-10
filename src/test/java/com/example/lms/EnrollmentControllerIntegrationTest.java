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
class EnrollmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getEnrollmentsByStudent_ExistingStudent_ReturnsEnrollments() throws Exception {
        mockMvc.perform(get("/api/enrollments/student/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getEnrollmentsByStudent_Student4_VerifyEnrollments() throws Exception {
        mockMvc.perform(get("/api/enrollments/student/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$[0].studentId").value(4));
    }

    @Test
    void getEnrollmentsByStudent_Student5_ReturnsEnrollments() throws Exception {
        mockMvc.perform(get("/api/enrollments/student/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));
    }

    @Test
    void getEnrollmentsByStudent_NonExistingStudent_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/enrollments/student/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getEnrollmentsByStudent_Student8_VerifyMultipleEnrollments() throws Exception {
        mockMvc.perform(get("/api/enrollments/student/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].studentId").value(8));
    }
}

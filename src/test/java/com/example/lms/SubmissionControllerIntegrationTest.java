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
class SubmissionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getSubmissionsByAssignment_ExistingAssignment_ReturnsSubmissions() throws Exception {
        mockMvc.perform(get("/api/submissions/assignment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getSubmissionsByAssignment_Assignment1_VerifySubmissionDetails() throws Exception {
        mockMvc.perform(get("/api/submissions/assignment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].score").value(98))
                .andExpect(jsonPath("$[0].studentId").value(4));
    }

    @Test
    void getSubmissionsByAssignment_NonExistingAssignment_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/submissions/assignment/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getSubmissionsByAssignment_VerifyContent() throws Exception {
        mockMvc.perform(get("/api/submissions/assignment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("public class HelloWorld { public static void main(String[] args) { System.out.println(\"Hello World\"); } }"));
    }

    @Test
    void getSubmissionsByAssignment_VerifyFeedback() throws Exception {
        mockMvc.perform(get("/api/submissions/assignment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].feedback").exists())
                .andExpect(jsonPath("$[0].submittedAt").exists());
    }
}
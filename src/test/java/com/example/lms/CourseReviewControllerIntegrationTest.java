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
class CourseReviewControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getReviewsByCourse_ExistingCourse_ReturnsReviews() throws Exception {
        mockMvc.perform(get("/api/reviews/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getReviewsByCourse_Course1_VerifyReviewDetails() throws Exception {
        mockMvc.perform(get("/api/reviews/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].rating").value(5))
                .andExpect(jsonPath("$[0].comment").value("Великолепный курс с понятным изложением материала!"));
    }

    @Test
    void getReviewsByCourse_Course3_VerifyReviewRating() throws Exception {
        mockMvc.perform(get("/api/reviews/course/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].rating").value(5))
                .andExpect(jsonPath("$[0].comment").value("Отличный преподаватель, очень информативный курс"));
    }

    @Test
    void getReviewsByCourse_NonExistingCourse_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/reviews/course/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getReviewsByCourse_Course1_VerifySecondReview() throws Exception {
        mockMvc.perform(get("/api/reviews/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].rating").value(4))
                .andExpect(jsonPath("$[1].comment").value("Качественный курс, хотелось бы больше практических заданий"));
    }
}
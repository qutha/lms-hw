package com.example.lms.controller;

import com.example.lms.model.CourseReview;
import com.example.lms.repo.CourseReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class CourseReviewController {

    private final CourseReviewRepository courseReviewRepo;

    @GetMapping
    public List<CourseReview> getAllReviews() {
        return courseReviewRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseReview> getReviewById(@PathVariable Long id) {
        Optional<CourseReview> review = courseReviewRepo.findById(id);
        return review.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{courseId}")
    public List<CourseReview> getReviewsByCourse(@PathVariable Long courseId) {
        return courseReviewRepo.findByCourseId(courseId);
    }

    @GetMapping("/student/{studentId}")
    public List<CourseReview> getReviewsByStudent(@PathVariable Long studentId) {
        return courseReviewRepo.findByStudentId(studentId);
    }

    @PostMapping
    public CourseReview createReview(@RequestBody CourseReview review) {
        return courseReviewRepo.save(review);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseReview> updateReview(@PathVariable Long id, @RequestBody CourseReview review) {
        if (courseReviewRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        review.setId(id);
        return ResponseEntity.ok(courseReviewRepo.save(review));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        if (courseReviewRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        courseReviewRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
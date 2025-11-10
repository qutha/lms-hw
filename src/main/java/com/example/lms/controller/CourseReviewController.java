package com.example.lms.controller;

import com.example.lms.dto.CourseReviewResponse;
import com.example.lms.dto.CreateCourseReviewRequest;
import com.example.lms.dto.UpdateCourseReviewRequest;
import com.example.lms.mapper.CourseReviewMapper;
import com.example.lms.model.Course;
import com.example.lms.model.CourseReview;
import com.example.lms.model.User;
import com.example.lms.repo.CourseRepository;
import com.example.lms.repo.CourseReviewRepository;
import com.example.lms.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class CourseReviewController {

    private final CourseReviewRepository courseReviewRepo;
    private final CourseRepository courseRepo;
    private final UserRepository userRepo;
    private final CourseReviewMapper courseReviewMapper;

    @GetMapping
    public List<CourseReviewResponse> getAllReviews() {
        return courseReviewRepo.findAll().stream()
                .map(courseReviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseReviewResponse> getReviewById(@PathVariable Long id) {
        Optional<CourseReview> review = courseReviewRepo.findById(id);
        return review.map(courseReviewMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{courseId}")
    public List<CourseReviewResponse> getReviewsByCourse(@PathVariable Long courseId) {
        return courseReviewRepo.findByCourseId(courseId).stream()
                .map(courseReviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/student/{studentId}")
    public List<CourseReviewResponse> getReviewsByStudent(@PathVariable Long studentId) {
        return courseReviewRepo.findByStudentId(studentId).stream()
                .map(courseReviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<CourseReviewResponse> createReview(@Valid @RequestBody CreateCourseReviewRequest request) {
        Course course = courseRepo.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Курс не найден"));
        User student = userRepo.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Студент не найден"));
        
        CourseReview review = courseReviewMapper.toEntity(request, course, student);
        CourseReview savedReview = courseReviewRepo.save(review);
        return ResponseEntity.ok(courseReviewMapper.toResponse(savedReview));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseReviewResponse> updateReview(@PathVariable Long id, 
                                                              @Valid @RequestBody UpdateCourseReviewRequest request) {
        CourseReview existingReview = courseReviewRepo.findById(id).orElse(null);
        if (existingReview == null) {
            return ResponseEntity.notFound().build();
        }
        
        Course course = courseRepo.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Курс не найден"));
        User student = userRepo.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Студент не найден"));
        
        CourseReview updatedReview = courseReviewMapper.toEntity(request, course, student, existingReview);
        CourseReview savedReview = courseReviewRepo.save(updatedReview);
        return ResponseEntity.ok(courseReviewMapper.toResponse(savedReview));
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
package com.example.lms.controller;

import com.example.lms.dto.CreateEnrollmentRequest;
import com.example.lms.dto.EnrollmentResponse;
import com.example.lms.dto.UpdateEnrollmentRequest;
import com.example.lms.mapper.EnrollmentMapper;
import com.example.lms.model.Course;
import com.example.lms.model.Enrollment;
import com.example.lms.model.User;
import com.example.lms.repo.CourseRepository;
import com.example.lms.repo.EnrollmentRepository;
import com.example.lms.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepo;
    private final CourseRepository courseRepo;
    private final UserRepository userRepo;
    private final EnrollmentMapper enrollmentMapper;

    @GetMapping
    public List<EnrollmentResponse> getAllEnrollments() {
        return enrollmentRepo.findAll().stream()
                .map(enrollmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> getEnrollmentById(@PathVariable Long id) {
        Optional<Enrollment> enrollment = enrollmentRepo.findById(id);
        return enrollment.map(enrollmentMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public List<EnrollmentResponse> getEnrollmentsByStudent(@PathVariable Long studentId) {
        return enrollmentRepo.findByStudentId(studentId).stream()
                .map(enrollmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/course/{courseId}")
    public List<EnrollmentResponse> getEnrollmentsByCourse(@PathVariable Long courseId) {
        return enrollmentRepo.findByCourseId(courseId).stream()
                .map(enrollmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<EnrollmentResponse> getEnrollmentByStudentAndCourse(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        Optional<Enrollment> enrollment = enrollmentRepo.findByStudentIdAndCourseId(studentId, courseId);
        return enrollment.map(enrollmentMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponse> createEnrollment(@Valid @RequestBody CreateEnrollmentRequest request) {
        User student = userRepo.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Студент не найден"));
        Course course = courseRepo.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Курс не найден"));
        
        Enrollment enrollment = enrollmentMapper.toEntity(request, student, course);
        Enrollment savedEnrollment = enrollmentRepo.save(enrollment);
        return ResponseEntity.ok(enrollmentMapper.toResponse(savedEnrollment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> updateEnrollment(@PathVariable Long id, 
                                                                @Valid @RequestBody UpdateEnrollmentRequest request) {
        Enrollment existingEnrollment = enrollmentRepo.findById(id).orElse(null);
        if (existingEnrollment == null) {
            return ResponseEntity.notFound().build();
        }
        
        User student = userRepo.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Студент не найден"));
        Course course = courseRepo.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Курс не найден"));
        
        Enrollment updatedEnrollment = enrollmentMapper.toEntity(request, student, course, existingEnrollment);
        Enrollment savedEnrollment = enrollmentRepo.save(updatedEnrollment);
        return ResponseEntity.ok(enrollmentMapper.toResponse(savedEnrollment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        if (enrollmentRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        enrollmentRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
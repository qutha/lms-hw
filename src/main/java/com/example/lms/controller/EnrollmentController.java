package com.example.lms.controller;

import com.example.lms.model.Enrollment;
import com.example.lms.repo.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepo;

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Long id) {
        Optional<Enrollment> enrollment = enrollmentRepo.findById(id);
        return enrollment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudent(@PathVariable Long studentId) {
        return enrollmentRepo.findByStudentId(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourse(@PathVariable Long courseId) {
        return enrollmentRepo.findByCourseId(courseId);
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Enrollment> getEnrollmentByStudentAndCourse(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        Optional<Enrollment> enrollment = enrollmentRepo.findByStudentIdAndCourseId(studentId, courseId);
        return enrollment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Enrollment createEnrollment(@RequestBody Enrollment enrollment) {
        return enrollmentRepo.save(enrollment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        if (enrollmentRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        enrollment.setId(id);
        return ResponseEntity.ok(enrollmentRepo.save(enrollment));
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
package com.example.lms.controller;

import com.example.lms.model.Course;
import com.example.lms.repo.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository courseRepo;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseRepo.findById(id);
        return course.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/teacher/{teacherId}")
    public List<Course> getCoursesByTeacher(@PathVariable Long teacherId) {
        return courseRepo.findByTeacherId(teacherId);
    }

    @GetMapping("/category/{categoryId}")
    public List<Course> getCoursesByCategoryId(@PathVariable Long categoryId) {
        return courseRepo.findByCategoryId(categoryId);
    }

    @GetMapping("/category/name/{categoryName}")
    public List<Course> getCoursesByCategoryName(@PathVariable String categoryName) {
        return courseRepo.findByCategoryName(categoryName);
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseRepo.save(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        if (courseRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        course.setId(id);
        return ResponseEntity.ok(courseRepo.save(course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (courseRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        courseRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
package com.example.lms.controller;

import com.example.lms.dto.CourseResponse;
import com.example.lms.dto.CreateCourseRequest;
import com.example.lms.dto.UpdateCourseRequest;
import com.example.lms.mapper.CourseMapper;
import com.example.lms.model.Category;
import com.example.lms.model.Course;
import com.example.lms.model.User;
import com.example.lms.repo.CategoryRepository;
import com.example.lms.repo.CourseRepository;
import com.example.lms.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository courseRepo;
    private final CategoryRepository categoryRepo;
    private final UserRepository userRepo;
    private final CourseMapper courseMapper;

    @GetMapping
    public List<CourseResponse> getAllCourses() {
        return courseRepo.findAll().stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseRepo.findById(id);
        return course.map(courseMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/teacher/{teacherId}")
    public List<CourseResponse> getCoursesByTeacher(@PathVariable Long teacherId) {
        return courseRepo.findByTeacherId(teacherId).stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/category/{categoryId}")
    public List<CourseResponse> getCoursesByCategoryId(@PathVariable Long categoryId) {
        return courseRepo.findByCategoryId(categoryId).stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/category/name/{categoryName}")
    public List<CourseResponse> getCoursesByCategoryName(@PathVariable String categoryName) {
        return courseRepo.findByCategoryName(categoryName).stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CreateCourseRequest request) {
        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));
        User teacher = userRepo.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Преподаватель не найден"));
        
        Course course = courseMapper.toEntity(request, category, teacher);
        Course savedCourse = courseRepo.save(course);
        return ResponseEntity.ok(courseMapper.toResponse(savedCourse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long id, 
                                                        @Valid @RequestBody UpdateCourseRequest request) {
        Course existingCourse = courseRepo.findById(id).orElse(null);
        if (existingCourse == null) {
            return ResponseEntity.notFound().build();
        }
        
        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));
        User teacher = userRepo.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Преподаватель не найден"));
        
        Course updatedCourse = courseMapper.toEntity(request, category, teacher, existingCourse);
        Course savedCourse = courseRepo.save(updatedCourse);
        return ResponseEntity.ok(courseMapper.toResponse(savedCourse));
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
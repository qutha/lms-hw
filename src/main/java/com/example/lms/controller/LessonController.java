package com.example.lms.controller;

import com.example.lms.dto.CreateLessonRequest;
import com.example.lms.dto.LessonResponse;
import com.example.lms.dto.UpdateLessonRequest;
import com.example.lms.mapper.LessonMapper;
import com.example.lms.model.CourseModule;
import com.example.lms.model.Lesson;
import com.example.lms.repo.CourseModuleRepository;
import com.example.lms.repo.LessonRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonRepository lessonRepo;
    private final CourseModuleRepository moduleRepo;
    private final LessonMapper lessonMapper;

    @GetMapping
    public List<LessonResponse> getAllLessons() {
        return lessonRepo.findAll().stream()
                .map(lessonMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonResponse> getLessonById(@PathVariable Long id) {
        Optional<Lesson> lesson = lessonRepo.findById(id);
        return lesson.map(lessonMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/module/{moduleId}")
    public List<LessonResponse> getLessonsByModule(@PathVariable Long moduleId) {
        return lessonRepo.findByCourseModuleId(moduleId).stream()
                .map(lessonMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<LessonResponse> createLesson(@Valid @RequestBody CreateLessonRequest request) {
        CourseModule module = moduleRepo.findById(request.getModuleId())
                .orElseThrow(() -> new RuntimeException("Модуль не найден"));

        Lesson lesson = lessonMapper.toEntity(request, module);
        Lesson savedLesson = lessonRepo.save(lesson);
        return ResponseEntity.ok(lessonMapper.toResponse(savedLesson));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonResponse> updateLesson(@PathVariable Long id,
                                                       @Valid @RequestBody UpdateLessonRequest request) {
        Lesson existingLesson = lessonRepo.findById(id).orElse(null);
        if (existingLesson == null) {
            return ResponseEntity.notFound().build();
        }

        Lesson updatedLesson = lessonMapper.toEntity(request, existingLesson.getCourseModule(), existingLesson);
        Lesson savedLesson = lessonRepo.save(updatedLesson);
        return ResponseEntity.ok(lessonMapper.toResponse(savedLesson));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        if (lessonRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        lessonRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
package com.example.lms.controller;

import com.example.lms.model.Lesson;
import com.example.lms.repo.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonRepository lessonRepo;

    @GetMapping
    public List<Lesson> getAllLessons() {
        return lessonRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        Optional<Lesson> lesson = lessonRepo.findById(id);
        return lesson.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/module/{moduleId}")
    public List<Lesson> getLessonsByModule(@PathVariable Long moduleId) {
        return lessonRepo.findByCourseModuleId(moduleId);
    }

    @PostMapping
    public Lesson createLesson(@RequestBody Lesson lesson) {
        return lessonRepo.save(lesson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        if (lessonRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        lesson.setId(id);
        return ResponseEntity.ok(lessonRepo.save(lesson));
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
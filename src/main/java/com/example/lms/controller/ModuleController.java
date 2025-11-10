package com.example.lms.controller;

import com.example.lms.model.CourseModule;
import com.example.lms.repo.CourseModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final CourseModuleRepository courseModuleRepo;

    @GetMapping
    public List<CourseModule> getAllModules() {
        return courseModuleRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseModule> getModuleById(@PathVariable Long id) {
        Optional<CourseModule> module = courseModuleRepo.findById(id);
        return module.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{courseId}")
    public List<CourseModule> getModulesByCourse(@PathVariable Long courseId) {
        return courseModuleRepo.findByCourseId(courseId);
    }

    @PostMapping
    public CourseModule createModule(@RequestBody CourseModule module) {
        return courseModuleRepo.save(module);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseModule> updateModule(@PathVariable Long id, @RequestBody CourseModule module) {
        if (courseModuleRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        module.setId(id);
        return ResponseEntity.ok(courseModuleRepo.save(module));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        if (courseModuleRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        courseModuleRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
package com.example.lms.controller;

import com.example.lms.dto.CreateModuleRequest;
import com.example.lms.dto.ModuleResponse;
import com.example.lms.dto.UpdateModuleRequest;
import com.example.lms.mapper.ModuleMapper;
import com.example.lms.model.Course;
import com.example.lms.model.CourseModule;
import com.example.lms.repo.CourseModuleRepository;
import com.example.lms.repo.CourseRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final CourseModuleRepository courseModuleRepo;
    private final CourseRepository courseRepo;
    private final ModuleMapper moduleMapper;

    @GetMapping
    public List<ModuleResponse> getAllModules() {
        return courseModuleRepo.findAll().stream()
                .map(moduleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleResponse> getModuleById(@PathVariable Long id) {
        Optional<CourseModule> module = courseModuleRepo.findById(id);
        return module.map(moduleMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{courseId}")
    public List<ModuleResponse> getModulesByCourse(@PathVariable Long courseId) {
        return courseModuleRepo.findByCourseId(courseId).stream()
                .map(moduleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ModuleResponse> createModule(@Valid @RequestBody CreateModuleRequest request) {
        Course course = courseRepo.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Курс не найден"));

        CourseModule module = moduleMapper.toEntity(request, course);
        CourseModule savedModule = courseModuleRepo.save(module);
        return ResponseEntity.ok(moduleMapper.toResponse(savedModule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuleResponse> updateModule(@PathVariable Long id,
                                                       @Valid @RequestBody UpdateModuleRequest request) {
        CourseModule existingModule = courseModuleRepo.findById(id).orElse(null);
        if (existingModule == null) {
            return ResponseEntity.notFound().build();
        }
        CourseModule updatedModule = moduleMapper.toEntity(request, existingModule.getCourse(), existingModule);
        CourseModule savedModule = courseModuleRepo.save(updatedModule);
        return ResponseEntity.ok(moduleMapper.toResponse(savedModule));
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
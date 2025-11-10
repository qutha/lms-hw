package com.example.lms.controller;

import com.example.lms.dto.AssignmentResponse;
import com.example.lms.dto.CreateAssignmentRequest;
import com.example.lms.dto.UpdateAssignmentRequest;
import com.example.lms.mapper.AssignmentMapper;
import com.example.lms.model.Assignment;
import com.example.lms.model.Lesson;
import com.example.lms.repo.AssignmentRepository;
import com.example.lms.repo.LessonRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentRepository assignmentRepo;
    private final LessonRepository lessonRepo;
    private final AssignmentMapper assignmentMapper;

    @GetMapping
    public List<AssignmentResponse> getAllAssignments() {
        return assignmentRepo.findAll().stream()
                .map(assignmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> getAssignmentById(@PathVariable Long id) {
        Optional<Assignment> assignment = assignmentRepo.findById(id);
        return assignment.map(assignmentMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AssignmentResponse> createAssignment(@Valid @RequestBody CreateAssignmentRequest request) {
        Lesson lesson = lessonRepo.findById(request.getLessonId())
                .orElseThrow(() -> new RuntimeException("Урок не найден"));

        Assignment assignment = assignmentMapper.toEntity(request, lesson);
        Assignment savedAssignment = assignmentRepo.save(assignment);
        return ResponseEntity.ok(assignmentMapper.toResponse(savedAssignment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentResponse> updateAssignment(@PathVariable Long id,
                                                               @Valid @RequestBody UpdateAssignmentRequest request) {
        Assignment existingAssignment = assignmentRepo.findById(id).orElse(null);
        if (existingAssignment == null) {
            return ResponseEntity.notFound().build();
        }

        Assignment updatedAssignment = assignmentMapper.toEntity(request, existingAssignment.getLesson(), existingAssignment);
        Assignment savedAssignment = assignmentRepo.save(updatedAssignment);
        return ResponseEntity.ok(assignmentMapper.toResponse(savedAssignment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        if (assignmentRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        assignmentRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
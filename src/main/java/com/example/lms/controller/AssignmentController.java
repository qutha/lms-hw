package com.example.lms.controller;

import com.example.lms.model.Assignment;
import com.example.lms.repo.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentRepository assignmentRepo;

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long id) {
        Optional<Assignment> assignment = assignmentRepo.findById(id);
        return assignment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentRepo.save(assignment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long id, @RequestBody Assignment assignment) {
        if (assignmentRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        assignment.setId(id);
        return ResponseEntity.ok(assignmentRepo.save(assignment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        if (assignmentRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        assignmentRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
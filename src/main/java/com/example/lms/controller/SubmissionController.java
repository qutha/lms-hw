package com.example.lms.controller;

import com.example.lms.model.Submission;
import com.example.lms.repo.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionRepository submissionRepo;

    @GetMapping
    public List<Submission> getAllSubmissions() {
        return submissionRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long id) {
        Optional<Submission> submission = submissionRepo.findById(id);
        return submission.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/assignment/{assignmentId}")
    public List<Submission> getSubmissionsByAssignment(@PathVariable Long assignmentId) {
        return submissionRepo.findByAssignmentId(assignmentId);
    }

    @GetMapping("/student/{studentId}")
    public List<Submission> getSubmissionsByStudent(@PathVariable Long studentId) {
        return submissionRepo.findByStudentId(studentId);
    }

    @GetMapping("/assignment/{assignmentId}/student/{studentId}")
    public ResponseEntity<Submission> getSubmissionByAssignmentAndStudent(
            @PathVariable Long assignmentId, @PathVariable Long studentId) {
        Optional<Submission> submission = submissionRepo.findByAssignmentIdAndStudentId(assignmentId, studentId);
        return submission.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Submission createSubmission(@RequestBody Submission submission) {
        return submissionRepo.save(submission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> updateSubmission(@PathVariable Long id, @RequestBody Submission submission) {
        if (submissionRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        submission.setId(id);
        return ResponseEntity.ok(submissionRepo.save(submission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        if (submissionRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        submissionRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
package com.example.lms.controller;

import com.example.lms.dto.CreateSubmissionRequest;
import com.example.lms.dto.SubmissionResponse;
import com.example.lms.dto.UpdateSubmissionRequest;
import com.example.lms.mapper.SubmissionMapper;
import com.example.lms.model.Assignment;
import com.example.lms.model.Submission;
import com.example.lms.model.User;
import com.example.lms.repo.AssignmentRepository;
import com.example.lms.repo.SubmissionRepository;
import com.example.lms.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionRepository submissionRepo;
    private final AssignmentRepository assignmentRepo;
    private final UserRepository userRepo;
    private final SubmissionMapper submissionMapper;

    @GetMapping
    public List<SubmissionResponse> getAllSubmissions() {
        return submissionRepo.findAll().stream()
                .map(submissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResponse> getSubmissionById(@PathVariable Long id) {
        Optional<Submission> submission = submissionRepo.findById(id);
        return submission.map(submissionMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/assignment/{assignmentId}")
    public List<SubmissionResponse> getSubmissionsByAssignment(@PathVariable Long assignmentId) {
        return submissionRepo.findByAssignmentId(assignmentId).stream()
                .map(submissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/student/{studentId}")
    public List<SubmissionResponse> getSubmissionsByStudent(@PathVariable Long studentId) {
        return submissionRepo.findByStudentId(studentId).stream()
                .map(submissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/assignment/{assignmentId}/student/{studentId}")
    public ResponseEntity<SubmissionResponse> getSubmissionByAssignmentAndStudent(
            @PathVariable Long assignmentId, @PathVariable Long studentId) {
        Optional<Submission> submission = submissionRepo.findByAssignmentIdAndStudentId(assignmentId, studentId);
        return submission.map(submissionMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SubmissionResponse> createSubmission(@Valid @RequestBody CreateSubmissionRequest request) {
        Assignment assignment = assignmentRepo.findById(request.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Задание не найдено"));
        User student = userRepo.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Студент не найден"));
        
        Submission submission = submissionMapper.toEntity(request, assignment, student);
        Submission savedSubmission = submissionRepo.save(submission);
        return ResponseEntity.ok(submissionMapper.toResponse(savedSubmission));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubmissionResponse> updateSubmission(@PathVariable Long id, 
                                                                @Valid @RequestBody UpdateSubmissionRequest request) {
        Submission existingSubmission = submissionRepo.findById(id).orElse(null);
        if (existingSubmission == null) {
            return ResponseEntity.notFound().build();
        }
        
        Assignment assignment = assignmentRepo.findById(request.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Задание не найдено"));
        User student = userRepo.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Студент не найден"));
        
        Submission updatedSubmission = submissionMapper.toEntity(request, assignment, student, existingSubmission);
        Submission savedSubmission = submissionRepo.save(updatedSubmission);
        return ResponseEntity.ok(submissionMapper.toResponse(savedSubmission));
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
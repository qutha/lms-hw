package com.example.lms.controller;

import com.example.lms.model.QuizSubmission;
import com.example.lms.repo.QuizSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quiz-submissions")
@RequiredArgsConstructor
public class QuizSubmissionController {

    private final QuizSubmissionRepository quizSubmissionRepo;

    @GetMapping
    public List<QuizSubmission> getAllQuizSubmissions() {
        return quizSubmissionRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizSubmission> getQuizSubmissionById(@PathVariable Long id) {
        Optional<QuizSubmission> quizSubmission = quizSubmissionRepo.findById(id);
        return quizSubmission.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public List<QuizSubmission> getQuizSubmissionsByStudent(@PathVariable Long studentId) {
        return quizSubmissionRepo.findByStudentId(studentId);
    }

    @GetMapping("/quiz/{quizId}")
    public List<QuizSubmission> getQuizSubmissionsByQuiz(@PathVariable Long quizId) {
        return quizSubmissionRepo.findByQuizId(quizId);
    }

    @GetMapping("/quiz/{quizId}/student/{studentId}")
    public ResponseEntity<QuizSubmission> getQuizSubmissionByQuizAndStudent(
            @PathVariable Long quizId, @PathVariable Long studentId) {
        Optional<QuizSubmission> quizSubmission = quizSubmissionRepo.findByQuizIdAndStudentId(quizId, studentId);
        return quizSubmission.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public QuizSubmission createQuizSubmission(@RequestBody QuizSubmission quizSubmission) {
        return quizSubmissionRepo.save(quizSubmission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizSubmission> updateQuizSubmission(@PathVariable Long id, @RequestBody QuizSubmission quizSubmission) {
        if (quizSubmissionRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        quizSubmission.setId(id);
        return ResponseEntity.ok(quizSubmissionRepo.save(quizSubmission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizSubmission(@PathVariable Long id) {
        if (quizSubmissionRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        quizSubmissionRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
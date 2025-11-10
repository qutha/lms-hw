package com.example.lms.controller;

import com.example.lms.dto.CreateQuizSubmissionRequest;
import com.example.lms.dto.QuizSubmissionResponse;
import com.example.lms.dto.UpdateQuizSubmissionRequest;
import com.example.lms.mapper.QuizSubmissionMapper;
import com.example.lms.model.Quiz;
import com.example.lms.model.QuizSubmission;
import com.example.lms.model.User;
import com.example.lms.repo.QuizRepository;
import com.example.lms.repo.QuizSubmissionRepository;
import com.example.lms.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quiz-submissions")
@RequiredArgsConstructor
public class QuizSubmissionController {

    private final QuizSubmissionRepository quizSubmissionRepo;
    private final QuizRepository quizRepo;
    private final UserRepository userRepo;
    private final QuizSubmissionMapper quizSubmissionMapper;

    @GetMapping
    public List<QuizSubmissionResponse> getAllQuizSubmissions() {
        return quizSubmissionRepo.findAll().stream()
                .map(quizSubmissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizSubmissionResponse> getQuizSubmissionById(@PathVariable Long id) {
        Optional<QuizSubmission> quizSubmission = quizSubmissionRepo.findById(id);
        return quizSubmission.map(quizSubmissionMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public List<QuizSubmissionResponse> getQuizSubmissionsByStudent(@PathVariable Long studentId) {
        return quizSubmissionRepo.findByStudentId(studentId).stream()
                .map(quizSubmissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/quiz/{quizId}")
    public List<QuizSubmissionResponse> getQuizSubmissionsByQuiz(@PathVariable Long quizId) {
        return quizSubmissionRepo.findByQuizId(quizId).stream()
                .map(quizSubmissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/quiz/{quizId}/student/{studentId}")
    public ResponseEntity<QuizSubmissionResponse> getQuizSubmissionByQuizAndStudent(
            @PathVariable Long quizId, @PathVariable Long studentId) {
        Optional<QuizSubmission> quizSubmission = quizSubmissionRepo.findByQuizIdAndStudentId(quizId, studentId);
        return quizSubmission.map(quizSubmissionMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<QuizSubmissionResponse> createQuizSubmission(@Valid @RequestBody CreateQuizSubmissionRequest request) {
        Quiz quiz = quizRepo.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Тест не найден"));
        User student = userRepo.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Студент не найден"));

        QuizSubmission quizSubmission = quizSubmissionMapper.toEntity(request, quiz, student);
        QuizSubmission savedSubmission = quizSubmissionRepo.save(quizSubmission);
        return ResponseEntity.ok(quizSubmissionMapper.toResponse(savedSubmission));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizSubmissionResponse> updateQuizSubmission(@PathVariable Long id,
                                                                       @Valid @RequestBody UpdateQuizSubmissionRequest request) {
        QuizSubmission existingSubmission = quizSubmissionRepo.findById(id).orElse(null);
        if (existingSubmission == null) {
            return ResponseEntity.notFound().build();
        }

        QuizSubmission updatedSubmission = quizSubmissionMapper.toEntity(request, existingSubmission.getQuiz(), existingSubmission.getStudent(), existingSubmission);
        QuizSubmission savedSubmission = quizSubmissionRepo.save(updatedSubmission);
        return ResponseEntity.ok(quizSubmissionMapper.toResponse(savedSubmission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizSubmission(@PathVariable Long id) {
        if (quizSubmissionRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        quizSubmissionRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
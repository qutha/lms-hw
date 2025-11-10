package com.example.lms.controller;

import com.example.lms.model.Quiz;
import com.example.lms.repo.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizRepository quizRepo;

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        Optional<Quiz> quiz = quizRepo.findById(id);
        return quiz.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<Quiz> getQuizByModule(@PathVariable Long moduleId) {
        Optional<Quiz> quiz = quizRepo.findByCourseModuleId(moduleId);
        return quiz.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz) {
        if (quizRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        quiz.setId(id);
        return ResponseEntity.ok(quizRepo.save(quiz));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        if (quizRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        quizRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
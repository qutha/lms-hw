package com.example.lms.controller;

import com.example.lms.model.Question;
import com.example.lms.repo.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionRepository questionRepo;

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        Optional<Question> question = questionRepo.findById(id);
        return question.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/quiz/{quizId}")
    public List<Question> getQuestionsByQuiz(@PathVariable Long quizId) {
        return questionRepo.findByQuizId(quizId);
    }

    @PostMapping
    public Question createQuestion(@RequestBody Question question) {
        return questionRepo.save(question);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        if (questionRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        question.setId(id);
        return ResponseEntity.ok(questionRepo.save(question));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        if (questionRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        questionRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
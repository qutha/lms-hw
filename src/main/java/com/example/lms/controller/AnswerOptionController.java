package com.example.lms.controller;

import com.example.lms.model.AnswerOption;
import com.example.lms.repo.AnswerOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/answer-options")
@RequiredArgsConstructor
public class AnswerOptionController {

    private final AnswerOptionRepository answerOptionRepo;

    @GetMapping
    public List<AnswerOption> getAllAnswerOptions() {
        return answerOptionRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerOption> getAnswerOptionById(@PathVariable Long id) {
        Optional<AnswerOption> answerOption = answerOptionRepo.findById(id);
        return answerOption.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/question/{questionId}")
    public List<AnswerOption> getAnswerOptionsByQuestion(@PathVariable Long questionId) {
        return answerOptionRepo.findByQuestionId(questionId);
    }

    @PostMapping
    public AnswerOption createAnswerOption(@RequestBody AnswerOption answerOption) {
        return answerOptionRepo.save(answerOption);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerOption> updateAnswerOption(@PathVariable Long id, @RequestBody AnswerOption answerOption) {
        if (answerOptionRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        answerOption.setId(id);
        return ResponseEntity.ok(answerOptionRepo.save(answerOption));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswerOption(@PathVariable Long id) {
        if (answerOptionRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        answerOptionRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
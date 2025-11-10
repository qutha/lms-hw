package com.example.lms.controller;

import com.example.lms.dto.AnswerOptionResponse;
import com.example.lms.dto.CreateAnswerOptionRequest;
import com.example.lms.dto.UpdateAnswerOptionRequest;
import com.example.lms.mapper.AnswerOptionMapper;
import com.example.lms.model.AnswerOption;
import com.example.lms.model.Question;
import com.example.lms.repo.AnswerOptionRepository;
import com.example.lms.repo.QuestionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/answer-options")
@RequiredArgsConstructor
public class AnswerOptionController {

    private final AnswerOptionRepository answerOptionRepo;
    private final QuestionRepository questionRepo;
    private final AnswerOptionMapper answerOptionMapper;

    @GetMapping
    public List<AnswerOptionResponse> getAllAnswerOptions() {
        return answerOptionRepo.findAll().stream()
                .map(answerOptionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerOptionResponse> getAnswerOptionById(@PathVariable Long id) {
        Optional<AnswerOption> answerOption = answerOptionRepo.findById(id);
        return answerOption.map(answerOptionMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/question/{questionId}")
    public List<AnswerOptionResponse> getAnswerOptionsByQuestion(@PathVariable Long questionId) {
        return answerOptionRepo.findByQuestionId(questionId).stream()
                .map(answerOptionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<AnswerOptionResponse> createAnswerOption(@Valid @RequestBody CreateAnswerOptionRequest request) {
        Question question = questionRepo.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Вопрос не найден"));

        AnswerOption answerOption = answerOptionMapper.toEntity(request, question);
        AnswerOption savedAnswerOption = answerOptionRepo.save(answerOption);
        return ResponseEntity.ok(answerOptionMapper.toResponse(savedAnswerOption));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerOptionResponse> updateAnswerOption(@PathVariable Long id,
                                                                   @Valid @RequestBody UpdateAnswerOptionRequest request) {
        AnswerOption existingAnswerOption = answerOptionRepo.findById(id)
                .orElse(null);
        if (existingAnswerOption == null) {
            return ResponseEntity.notFound().build();
        }

        Question question = existingAnswerOption.getQuestion();

        AnswerOption updatedAnswerOption = answerOptionMapper.toEntity(request, question, existingAnswerOption);
        AnswerOption savedAnswerOption = answerOptionRepo.save(updatedAnswerOption);
        return ResponseEntity.ok(answerOptionMapper.toResponse(savedAnswerOption));
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
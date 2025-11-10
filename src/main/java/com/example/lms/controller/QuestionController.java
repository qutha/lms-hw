package com.example.lms.controller;

import com.example.lms.dto.CreateQuestionRequest;
import com.example.lms.dto.QuestionResponse;
import com.example.lms.dto.UpdateQuestionRequest;
import com.example.lms.mapper.QuestionMapper;
import com.example.lms.model.Question;
import com.example.lms.model.Quiz;
import com.example.lms.repo.QuestionRepository;
import com.example.lms.repo.QuizRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionRepository questionRepo;
    private final QuizRepository quizRepo;
    private final QuestionMapper questionMapper;

    @GetMapping
    public List<QuestionResponse> getAllQuestions() {
        return questionRepo.findAll().stream()
                .map(questionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        Optional<Question> question = questionRepo.findById(id);
        return question.map(questionMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/quiz/{quizId}")
    public List<QuestionResponse> getQuestionsByQuiz(@PathVariable Long quizId) {
        return questionRepo.findByQuizId(quizId).stream()
                .map(questionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(@Valid @RequestBody CreateQuestionRequest request) {
        Quiz quiz = quizRepo.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Тест не найден"));

        Question question = questionMapper.toEntity(request, quiz);
        Question savedQuestion = questionRepo.save(question);
        return ResponseEntity.ok(questionMapper.toResponse(savedQuestion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Long id,
                                                           @Valid @RequestBody UpdateQuestionRequest request) {
        Question existingQuestion = questionRepo.findById(id).orElse(null);
        if (existingQuestion == null) {
            return ResponseEntity.notFound().build();
        }

        Question updatedQuestion = questionMapper.toEntity(request, existingQuestion.getQuiz(), existingQuestion);
        Question savedQuestion = questionRepo.save(updatedQuestion);
        return ResponseEntity.ok(questionMapper.toResponse(savedQuestion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        if (questionRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        questionRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
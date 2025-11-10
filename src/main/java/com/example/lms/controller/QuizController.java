package com.example.lms.controller;

import com.example.lms.dto.CreateQuizRequest;
import com.example.lms.dto.QuizResponse;
import com.example.lms.dto.UpdateQuizRequest;
import com.example.lms.mapper.QuizMapper;
import com.example.lms.model.CourseModule;
import com.example.lms.model.Quiz;
import com.example.lms.repo.CourseModuleRepository;
import com.example.lms.repo.QuizRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizRepository quizRepo;
    private final CourseModuleRepository moduleRepo;
    private final QuizMapper quizMapper;

    @GetMapping
    public List<QuizResponse> getAllQuizzes() {
        return quizRepo.findAll().stream()
                .map(quizMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable Long id) {
        Optional<Quiz> quiz = quizRepo.findById(id);
        return quiz.map(quizMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<QuizResponse> getQuizByModule(@PathVariable Long moduleId) {
        Optional<Quiz> quiz = quizRepo.findByCourseModuleId(moduleId);
        return quiz.map(quizMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<QuizResponse> createQuiz(@Valid @RequestBody CreateQuizRequest request) {
        CourseModule module = moduleRepo.findById(request.getModuleId())
                .orElseThrow(() -> new RuntimeException("Модуль не найден"));
        
        Quiz quiz = quizMapper.toEntity(request, module);
        Quiz savedQuiz = quizRepo.save(quiz);
        return ResponseEntity.ok(quizMapper.toResponse(savedQuiz));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizResponse> updateQuiz(@PathVariable Long id, 
                                                    @Valid @RequestBody UpdateQuizRequest request) {
        Quiz existingQuiz = quizRepo.findById(id).orElse(null);
        if (existingQuiz == null) {
            return ResponseEntity.notFound().build();
        }
        
        CourseModule module = moduleRepo.findById(request.getModuleId())
                .orElseThrow(() -> new RuntimeException("Модуль не найден"));
        
        Quiz updatedQuiz = quizMapper.toEntity(request, module, existingQuiz);
        Quiz savedQuiz = quizRepo.save(updatedQuiz);
        return ResponseEntity.ok(quizMapper.toResponse(savedQuiz));
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
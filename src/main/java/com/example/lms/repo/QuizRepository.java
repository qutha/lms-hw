package com.example.lms.repo;

import com.example.lms.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByCourseModuleId(Long courseModuleId);
}

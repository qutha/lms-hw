package com.example.lms.repo;

import com.example.lms.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeacherId(Long teacherId);

    List<Course> findByCategoryId(Long categoryId);

    List<Course> findByCategoryName(String categoryName);
}


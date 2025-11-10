package com.example.lms.controller;

import com.example.lms.dto.CategoryResponse;
import com.example.lms.dto.CreateCategoryRequest;
import com.example.lms.dto.UpdateCategoryRequest;
import com.example.lms.mapper.CategoryMapper;
import com.example.lms.model.Category;
import com.example.lms.repo.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepo;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryRepo.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        return category.map(categoryMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable String name) {
        Optional<Category> category = categoryRepo.findByName(name);
        return category.map(categoryMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        Category savedCategory = categoryRepo.save(category);
        return ResponseEntity.ok(categoryMapper.toResponse(savedCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, 
                                                            @Valid @RequestBody UpdateCategoryRequest request) {
        Category existingCategory = categoryRepo.findById(id).orElse(null);
        if (existingCategory == null) {
            return ResponseEntity.notFound().build();
        }
        Category updatedCategory = categoryMapper.toEntity(request, existingCategory);
        Category savedCategory = categoryRepo.save(updatedCategory);
        return ResponseEntity.ok(categoryMapper.toResponse(savedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (categoryRepo.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        categoryRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
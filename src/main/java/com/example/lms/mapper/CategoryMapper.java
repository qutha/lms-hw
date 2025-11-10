package com.example.lms.mapper;

import com.example.lms.dto.CategoryResponse;
import com.example.lms.dto.CreateCategoryRequest;
import com.example.lms.dto.UpdateCategoryRequest;
import com.example.lms.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        return category;
    }

    public Category toEntity(UpdateCategoryRequest request, Category existingCategory) {
        existingCategory.setName(request.getName());
        return existingCategory;
    }

    public CategoryResponse toResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        return response;
    }
}

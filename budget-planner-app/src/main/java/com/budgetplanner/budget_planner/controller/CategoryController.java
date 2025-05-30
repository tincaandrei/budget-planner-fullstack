package com.budgetplanner.budget_planner.controller;

import com.budgetplanner.budget_planner.DTOs.CategoryDTO;
import com.budgetplanner.budget_planner.mappers.CategoryMapper;
import com.budgetplanner.budget_planner.model.Category;
import com.budgetplanner.budget_planner.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories()
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    @PostMapping
    public ResponseEntity saveCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toEntity(categoryDTO);
        Category saved = categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.toDTO(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryMapper.toDTO(category));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity getCategoriesByType(@PathVariable String type) {
        // triggering exception if type does not exist
        categoryService.getCategoriesByType(type);


        List<CategoryDTO> categories = categoryService.getCategoriesByType(type.toUpperCase())
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
        // triggernig exception if the category does not exist
        if (categories.isEmpty()) {
            throw new NoSuchElementException("Category " + type +" was not found");
        }
        return ResponseEntity.ok(categories);
    }
}
package com.budgetplanner.budget_planner.service;

import com.budgetplanner.budget_planner.model.Category;
import com.budgetplanner.budget_planner.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(long givenId) {
        Category category = categoryRepository.findById(givenId);
        if(category != null) {
            return category;
        } else {
            throw new NoSuchElementException("Category with id " + givenId + " was not found");
        }
    }

    public Category updateCategory(Category category) {
        return categoryRepository.updateById(category);
    }

    public void deleteCategory(Category category) {
        categoryRepository.deleteById(category.getId());
    }
}

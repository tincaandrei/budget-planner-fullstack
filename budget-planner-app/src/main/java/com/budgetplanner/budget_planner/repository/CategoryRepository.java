package com.budgetplanner.budget_planner.repository;

import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepository {
    private final List<Category> categories = new ArrayList<>();

    public void saveAll(List<Category> newCategories) {
        categories.addAll(newCategories);
    }

    public void save(Category c){
        categories.add(c);
    }
    public List<Category> findAll() {
        return categories;
    }

    public Category findById(Long id) {
        return categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Category updateById(Category category) {
        Category newCategory = findById(category.getId());
        if (newCategory != null) {
            newCategory.setName(category.getName());
            newCategory.setType(category.getType());
        }
        return newCategory;
    }

    public void deleteById(Long id) {
        categories.removeIf(category -> category.getId().equals(id));
    }
}

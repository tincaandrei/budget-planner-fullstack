package com.budgetplanner.budget_planner.mappers;

import com.budgetplanner.budget_planner.DTOs.CategoryDTO;
import com.budgetplanner.budget_planner.model.Category;
import org.springframework.stereotype.Component;


@Component
public class CategoryMapper {
    public CategoryDTO toDTO(Category category) {
        if (category == null) return null;
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setType(category.getType());
        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) return null;
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setType(dto.getType());
        return category;
    }
}

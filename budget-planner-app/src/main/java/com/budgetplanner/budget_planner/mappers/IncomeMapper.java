package com.budgetplanner.budget_planner.mappers;

import com.budgetplanner.budget_planner.DTOs.IncomeDTO;
import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.model.Category;
import com.budgetplanner.budget_planner.model.Income;
import org.springframework.stereotype.Component;


@Component
public class IncomeMapper {
    public IncomeDTO toDTO(Income income) {
        if (income == null) return null;
        IncomeDTO dto = new IncomeDTO();
        dto.setId(income.getId());
        dto.setAmount(income.getAmount());
        dto.setDate(income.getDate());
        dto.setDescription(income.getDescription());
        dto.setBudgetId(income.getBudget().getId());
        dto.setCategoryId(income.getCategory().getId());
        return dto;
    }

    public Income toEntity(IncomeDTO dto, Budget budget, Category category) {
        if (dto == null) return null;
        Income income = new Income();
        income.setId(dto.getId());
        income.setAmount(dto.getAmount());
        income.setDate(dto.getDate());
        income.setDescription(dto.getDescription());
        income.setBudget(budget);
        income.setCategory(category);
        return income;
    }
}

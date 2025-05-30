package com.budgetplanner.budget_planner.mappers;

import com.budgetplanner.budget_planner.DTOs.ExpenseDTO;
import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.model.Category;
import com.budgetplanner.budget_planner.model.Expense;
import org.springframework.stereotype.Component;


@Component
public class ExpenseMapper {
    public ExpenseDTO toDTO(Expense expense) {
        if (expense == null) return null;
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setAmount(expense.getAmount());
        dto.setDate(expense.getDate());
        dto.setDescription(expense.getDescription());
        dto.setBudgetId(expense.getBudget().getId());
        dto.setCategoryId(expense.getCategory().getId());
        return dto;
    }

    public Expense toEntity(ExpenseDTO dto, Budget budget, Category category) {
        if (dto == null) return null;
        Expense expense = new Expense();
        expense.setId(dto.getId());
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        expense.setDescription(dto.getDescription());
        expense.setBudget(budget);
        expense.setCategory(category);
        return expense;
    }
}

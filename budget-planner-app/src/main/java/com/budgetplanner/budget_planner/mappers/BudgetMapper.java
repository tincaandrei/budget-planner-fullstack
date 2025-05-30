package com.budgetplanner.budget_planner.mappers;

import com.budgetplanner.budget_planner.DTOs.BudgetDTO;
import com.budgetplanner.budget_planner.DTOs.ExpenseDTO;
import com.budgetplanner.budget_planner.DTOs.IncomeDTO;
import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.model.User;
import org.springframework.stereotype.Component;

@Component
public class BudgetMapper {
    public BudgetDTO toDTO(Budget budget) {
        if (budget == null) return null;

        BudgetDTO dto = new BudgetDTO();
        dto.setId(budget.getId());
        dto.setName(budget.getName());
        dto.setTotalAmount(budget.getTotalAmount());
        dto.setUserId(budget.getUser().getId());

        if (budget.getExpenses() != null) {
            dto.setExpenses(
                    budget.getExpenses().stream()
                            .map(exp -> {
                                ExpenseDTO e = new ExpenseDTO();
                                e.setId(exp.getId());
                                e.setAmount(exp.getAmount());
                                e.setDate(exp.getDate());
                                e.setDescription(exp.getDescription());
                                e.setCategoryId(exp.getCategory().getId());


                                e.setBudgetId(budget.getId());
                                return e;
                            })
                            .toList()
            );
        }

        if (budget.getIncomes() != null) {
            dto.setIncomes(
                    budget.getIncomes().stream()
                            .map(inc -> {
                                IncomeDTO i = new IncomeDTO();
                                i.setId(inc.getId());
                                i.setAmount(inc.getAmount());
                                i.setDate(inc.getDate());
                                i.setDescription(inc.getDescription());
                                i.setCategoryId(inc.getCategory().getId());
                                return i;
                            })
                            .toList()
            );
        }

        return dto;
    }

    public Budget toEntity(BudgetDTO dto) {
        if (dto == null) return null;
        Budget budget = new Budget();
        budget.setId(dto.getId());
        budget.setName(dto.getName());
        budget.setTotalAmount(dto.getTotalAmount());
        User user = new User();
        user.setId(dto.getUserId());
        budget.setUser(user);
        return budget;
    }
}

package com.budgetplanner.budget_planner.DTOs;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class BudgetDTO {
    private Long id;
    private String name;
    private Double totalAmount;
    private Long userId;
    private List<ExpenseDTO> expenses;
    private List<IncomeDTO> incomes;
}

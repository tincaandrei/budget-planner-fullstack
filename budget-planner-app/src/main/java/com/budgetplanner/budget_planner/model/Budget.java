package com.budgetplanner.budget_planner.model;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Budget {
    private Long id;
    private String name;
    private Double totalAmount;
    private List<Expense> expenses;
    private List<Income> incomes;
    private Long userId;
}

package com.budgetplanner.budget_planner.DTOs;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Setter
@Getter

public class ExpenseDTO {
    private Long id;
    private Double amount;
    private String description;
    private LocalDate date;
    private Long budgetId;
    private Long categoryId;
}

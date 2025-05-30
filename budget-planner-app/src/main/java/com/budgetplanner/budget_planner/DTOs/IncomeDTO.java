package com.budgetplanner.budget_planner.DTOs;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class IncomeDTO {
    private Long id;
    private Double amount;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Long budgetId;
    private Long categoryId;
}

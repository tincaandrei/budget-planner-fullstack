package com.budgetplanner.budget_planner.strategy;

import com.budgetplanner.budget_planner.model.Expense;

import java.util.List;

public interface ExpenseReportStrategy {
    String generateReport(List<Expense> expenses);
}
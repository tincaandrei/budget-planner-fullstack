package com.budgetplanner.budget_planner.exceptions;

public class IncomeNotFoundException extends RuntimeException {
    public IncomeNotFoundException(Long id) {
        super("Income with ID " + id + " was not found.");
    }
}

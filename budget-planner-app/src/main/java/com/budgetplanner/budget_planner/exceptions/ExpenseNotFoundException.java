package com.budgetplanner.budget_planner.exceptions;

public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(Long id) {
        super("Expense with ID " + id + " was not found.");
    }
}

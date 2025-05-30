package com.budgetplanner.budget_planner.exceptions;

public class InvalidIncomeException extends RuntimeException {
    public InvalidIncomeException(String message) {
        super(message);
    }
}
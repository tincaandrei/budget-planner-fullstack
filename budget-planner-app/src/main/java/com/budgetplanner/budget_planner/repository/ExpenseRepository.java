package com.budgetplanner.budget_planner.repository;

import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.model.Expense;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseRepository {
    private final List<Expense> expenses = new ArrayList<>();

    public void saveAll(List<Expense> newExpenses) {
        expenses.addAll(newExpenses);
    }
    public void save(Expense e){
        expenses.add(e);
    }
    public List<Expense> findAll() {
        return expenses;
    }

    public Expense findById(Long id) {
        return expenses.stream()
                .filter(expense -> expense.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Expense updateById(Expense expense) {
        Expense newExpense = findById(expense.getId());
        if (newExpense != null) {
            newExpense.setDescription(expense.getDescription());
            newExpense.setAmount(expense.getAmount());
            newExpense.setDate(expense.getDate());
            newExpense.setBudgetId(expense.getBudgetId());
            newExpense.setCategory(expense.getCategory());
        }
        return newExpense;
    }

    public void deleteById(Long id) {
        expenses.removeIf(expense -> expense.getId().equals(id));
    }
}

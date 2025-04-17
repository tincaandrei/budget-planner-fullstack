package com.budgetplanner.budget_planner.service;

import com.budgetplanner.budget_planner.model.Expense;
import com.budgetplanner.budget_planner.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }


    public void addExpense(Expense expense){
        expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(long givenId) {
        Expense expense = expenseRepository.findById(givenId);
        if(expense != null) {
            return expense;
        } else {
            throw new NoSuchElementException("Expense with id " + givenId + " was not found");
        }
    }

    public Expense updateExpense(Expense expense) {
        return expenseRepository.updateById(expense);
    }

    public void deleteExpense(Expense expense) {
        expenseRepository.deleteById(expense.getId());
    }
}

package com.budgetplanner.budget_planner.service;

import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.model.Expense;
import com.budgetplanner.budget_planner.repository.BudgetRepository;
import com.budgetplanner.budget_planner.repository.ExpenseRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final SimpMessagingTemplate messagingTemplate;



    public ExpenseService(ExpenseRepository expenseRepository, SimpMessagingTemplate messagingTemplate) {
        this.expenseRepository = expenseRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public Expense addExpense(Expense expense) {

        Expense newExpense = expenseRepository.save(expense);

        String notificationMessage = " New expense successfully added " + newExpense.getAmount() + " RON " + newExpense.getDescription();
        Long userId = newExpense.getBudget().getUser().getId();
        System.out.println("Sending to topic: /topic/expense/" + userId);

        messagingTemplate.convertAndSend("/topic/expense/" + userId, notificationMessage);

        return newExpense;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(long givenId) {
        return expenseRepository.findById(givenId)
                .orElseThrow(() -> new NoSuchElementException("Expense not found"));
    }

    public Expense updateExpense(Expense expense) {
        if(!expenseRepository.existsById(expense.getId())) {
            throw new NoSuchElementException("Expense not found");
        }
        return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByMonth(Long userId, int month, int year) {
        return expenseRepository.findByUserAndMonth(userId, month, year);
    }


    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}

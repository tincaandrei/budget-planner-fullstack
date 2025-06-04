package com.budgetplanner.budget_planner.service;

import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.model.Expense;
import com.budgetplanner.budget_planner.repository.BudgetRepository;
import com.budgetplanner.budget_planner.repository.ExpenseRepository;
import com.budgetplanner.budget_planner.strategy.CSVExpenseReportStrategy;
import com.budgetplanner.budget_planner.strategy.ExpenseReportStrategy;
import com.budgetplanner.budget_planner.strategy.XMLExpenseReportStrategy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

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



    public List<Expense> getExpensesByMonth(Long userId, int month, int year) {
        return expenseRepository.findByUserAndMonth(userId, month, year);
    }



    public String getReport(Long userId, String format) {
        List<Expense> expenses = expenseRepository.findAllByUserId(userId);

        ExpenseReportStrategy strategy = switch (format.toLowerCase()) {
            case "xml" -> new XMLExpenseReportStrategy();
            case "csv" -> new CSVExpenseReportStrategy();
            default -> throw new IllegalArgumentException("Unsupported format");
        };

        return strategy.generateReport(expenses);
    }

    public List<Map<String, Object>> getCategoryChartData(Long userId, int month, int year) {
        List<Object[]> rawData = expenseRepository.getCategoryExpenseTotals(userId, month, year);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rawData) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("category", row[0]);
            entry.put("total", row[1]);
            result.add(entry);
        }
        return result;
    }


}

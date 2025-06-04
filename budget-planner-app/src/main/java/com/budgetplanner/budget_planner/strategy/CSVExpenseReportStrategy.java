package com.budgetplanner.budget_planner.strategy;

import com.budgetplanner.budget_planner.model.Expense;

import java.util.List;

public class CSVExpenseReportStrategy implements ExpenseReportStrategy {
    @Override
    public String generateReport(List<Expense> expenses) {
        StringBuilder sb = new StringBuilder();
        sb.append("Amount,Category,Date\n");
        for (Expense e : expenses) {
            sb.append(e.getAmount()).append(",");
            sb.append(e.getCategory().getName()).append(",");
            sb.append(e.getDate()).append("\n");
        }
        return sb.toString();
    }
}

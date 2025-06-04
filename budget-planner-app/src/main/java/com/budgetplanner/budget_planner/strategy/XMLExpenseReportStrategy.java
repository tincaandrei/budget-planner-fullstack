package com.budgetplanner.budget_planner.strategy;

import com.budgetplanner.budget_planner.model.Expense;

import java.util.List;

public class XMLExpenseReportStrategy implements ExpenseReportStrategy {
    @Override
    public String generateReport(List<Expense> expenses) {
        StringBuilder sb = new StringBuilder();
        sb.append("<expenses>\n");
        for (Expense e : expenses) {
            sb.append("  <expense>\n");
            sb.append("    <amount>").append(e.getAmount()).append("</amount>\n");
            sb.append("    <category>").append(e.getCategory().getName()).append("</category>\n");
            sb.append("    <date>").append(e.getDate()).append("</date>\n");
            sb.append("  </expense>\n");
        }
        sb.append("</expenses>");
        return sb.toString();
    }
}

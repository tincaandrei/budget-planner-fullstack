package com.budgetplanner.budget_planner.controller;


import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.service.BudgetService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/budgets")
public class BudgetController {
    private final BudgetService budgetService;
    public BudgetController (BudgetService budgetService){
        this.budgetService = budgetService;
    }
    @GetMapping
    public String showBudgets(Model model){
        List<Budget> budgets = budgetService.getAllBudgets();
        model.addAttribute("budgets", budgets);
        return "budgets/list-budgets";
    }
    @PostMapping("/save")
    public String saveBudget(@ModelAttribute("budget") Budget budget) {
        budgetService.addBudget(budget);
        return "redirect:/budgets";
    }
}

package com.budgetplanner.budget_planner.repository;

import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.model.Income;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class IncomeRepository {
    private final List<Income> incomes = new ArrayList<>();

    public void saveAll(List<Income> newIncomes) {
        incomes.addAll(newIncomes);
    }
    public void save(Income i){
        incomes.add(i);
    }
    public List<Income> findAll() {
        return incomes;
    }

    public Income findById(Long id) {
        return incomes.stream()
                .filter(income -> income.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Income updateById(Income income) {
        Income newIncome = findById(income.getId());
        if (newIncome != null) {
            newIncome.setDescription(income.getDescription());
            newIncome.setAmount(income.getAmount());
            newIncome.setDate(income.getDate());
            newIncome.setBudgetId(income.getBudgetId());
            newIncome.setCategory(income.getCategory());
        }
        return newIncome;
    }

    public void deleteById(Long id) {
        incomes.removeIf(income -> income.getId().equals(id));
    }
}

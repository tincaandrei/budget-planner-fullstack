package com.budgetplanner.budget_planner.repository;

import com.budgetplanner.budget_planner.model.Budget;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository

/*findAll(), findById(), update(), delete(), saveAll();
* */


public class BudgetRepository {
    private final List<Budget> budgets = new ArrayList<>();

    public void saveAll(List<Budget> newBudgets){
        budgets.addAll(newBudgets);
    }
    public void save(Budget budget){
        budgets.add(budget);
    }
    public List<Budget> findAll(){
        return budgets;
    }
    public Budget findById(Long id){
        return budgets.stream()
                .filter(budget -> budget.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    public Budget updateById(Budget budget){
        Budget newBudget = findById(budget.getId());
        if(newBudget != null){
            newBudget.setName(budget.getName());
            newBudget.setExpenses(budget.getExpenses());
            newBudget.setIncomes(budget.getIncomes());
        }
        return newBudget;
    }
    public void deleteById(Long id){
        budgets.removeIf(budget -> budget.getId().equals(id));
    }

}

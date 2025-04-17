package com.budgetplanner.budget_planner.service;

import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service



public class BudgetService {
    private final BudgetRepository budgetRepository;


    public BudgetService(BudgetRepository budgetRepository){
        this.budgetRepository = budgetRepository;
    }


    public void addBudget(Budget budget){
        budgetRepository.save(budget);
    }
    public List<Budget> getAllBudgets(){
        return budgetRepository.findAll();
    }

    public Budget getBudgetById(long givenId){
        Budget budget =budgetRepository.findById(givenId);
        if(budget!=null){
            return budget;
        }else {
            throw new NoSuchElementException("Budget with id" + givenId + "was not found");
        }
    }
    public Budget updateBudget(Budget budget){
        return budgetRepository.updateById(budget);
    }
    public void deleteBudget(Budget budget){
        budgetRepository.deleteById(budget.getId());
    }
}

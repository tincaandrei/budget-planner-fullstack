package com.budgetplanner.budget_planner.service;

import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service



public class BudgetService {
    private final BudgetRepository budgetRepository;


    public BudgetService(BudgetRepository budgetRepository){
        this.budgetRepository = budgetRepository;
    }


    public Budget addBudget(Budget budget){
        return budgetRepository.save(budget);
    }


    public List<Budget> getAllBudgets(){
        return budgetRepository.findAll();
    }

    public Budget getBudgetById(long givenId){
        return budgetRepository.findById(givenId)
                .orElseThrow(() -> new NoSuchElementException("Budget not found"));
    }
    public Budget updateBudget(Budget budget){
        if(!budgetRepository.existsById(budget.getId())){
            throw new NoSuchElementException("Budget not found");
        }
        return budgetRepository.save(budget);
    }


    public void deleteBudget(Budget budget){
        budgetRepository.deleteById(budget.getId());
    }
    public List<Budget> getBudgetsByUserId(Long userId) {
       return budgetRepository.findByUserId(userId);
    }



}

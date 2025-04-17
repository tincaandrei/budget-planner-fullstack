package com.budgetplanner.budget_planner.service;

import com.budgetplanner.budget_planner.model.Income;
import com.budgetplanner.budget_planner.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public void addIncome(Income income) {
        incomeRepository.save(income);
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public Income getIncomeById(long givenId) {
        Income income = incomeRepository.findById(givenId);
        if(income != null) {
            return income;
        } else {
            throw new NoSuchElementException("Income with id " + givenId + " was not found");
        }
    }

    public Income updateIncome(Income income) {
        return incomeRepository.updateById(income);
    }

    public void deleteIncome(Income income) {
        incomeRepository.deleteById(income.getId());
    }
}

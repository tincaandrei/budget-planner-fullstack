package com.budgetplanner.budget_planner.service;

import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.model.Income;
import com.budgetplanner.budget_planner.repository.BudgetRepository;
import com.budgetplanner.budget_planner.repository.IncomeRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final BudgetRepository budgetRepository;
    private final SimpMessagingTemplate messagingTemplate;



    public IncomeService(IncomeRepository incomeRepository, BudgetRepository budgetRepository, SimpMessagingTemplate messagingTemplate) {
        this.incomeRepository = incomeRepository;
        this.budgetRepository = budgetRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public Income addIncome(Income income) {

        Income newIncome = incomeRepository.save(income);

        String notificationMessage = " New income successfully added " + newIncome.getAmount() + " RON " + newIncome.getDescription();
        Long userId = newIncome.getBudget().getUser().getId();
        System.out.println("USER ID IS ------------->" + userId);
        messagingTemplate.convertAndSend("/topic/income/" + userId, notificationMessage);



        return newIncome;
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public Income getIncomeById(long givenId) {
       return incomeRepository.findById(givenId)
               .orElseThrow(() -> new NoSuchElementException("income not found"));
    }

    public Income updateIncome(Income income) {
        if(!incomeRepository.existsById(income.getId())) {
            throw new NoSuchElementException("income with id " + income.getId() + " not found");
        }
        return incomeRepository.save(income);
    }

    public List<Income> getIncomeByMonth(Long userId, int month, int year) {
        return incomeRepository.findByUserAndMonth(userId, month, year);
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }
}

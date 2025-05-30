package com.budgetplanner.budget_planner.controller;

import com.budgetplanner.budget_planner.DTOs.ExpenseDTO;
import com.budgetplanner.budget_planner.DTOs.IncomeDTO;
import com.budgetplanner.budget_planner.exceptions.IncomeNotFoundException;
import com.budgetplanner.budget_planner.exceptions.InvalidIncomeException;
import com.budgetplanner.budget_planner.mappers.IncomeMapper;
import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.model.Category;
import com.budgetplanner.budget_planner.model.Income;
import com.budgetplanner.budget_planner.repository.BudgetRepository;
import com.budgetplanner.budget_planner.service.IncomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;
    private final IncomeMapper incomeMapper;
    private final BudgetRepository budgetRepository;

    public IncomeController(IncomeService incomeService, IncomeMapper incomeMapper, BudgetRepository budgetRepository) {
        this.incomeService = incomeService;
        this.incomeMapper = incomeMapper;
        this.budgetRepository = budgetRepository;
    }

    // GET all incomes
    @GetMapping
    public List<IncomeDTO> getAllIncomes() {
        return incomeService.getAllIncomes()
                .stream()
                .map(incomeMapper::toDTO)
                .toList();
    }

    // GET one income by ID
    @GetMapping("/{id}")
    public IncomeDTO getIncomeById(@PathVariable Long id) {
        Income income = incomeService.getIncomeById(id);
        return incomeMapper.toDTO(income);
    }

    // POST a new income
    @PostMapping
    public IncomeDTO createIncome(@RequestBody IncomeDTO dto) {
        if(dto.getBudgetId() == null)
            throw new InvalidIncomeException("Budget id is required");

        Budget budget = budgetRepository.findById(dto.getBudgetId())
                .orElseThrow(() -> new InvalidIncomeException("Budget not found for this income"));

        Category category = new Category();
        category.setId(dto.getCategoryId());

        Income saved = incomeService.addIncome(
                incomeMapper.toEntity(dto, budget, category)
        );
        return incomeMapper.toDTO(saved);
    }

    // DELETE an income
    @DeleteMapping("/{id}")
    public void deleteIncome(@PathVariable Long id) {
        Income income = incomeService.getIncomeById(id);
        if (income == null) throw  new IncomeNotFoundException(id);
        incomeService.deleteIncome(id);
    }
    // Get incomes by date
    @GetMapping("/user/{userId}/month")
    public List<IncomeDTO> getMonthlyIncomes(@PathVariable Long userId,
                                               @RequestParam int month,
                                               @RequestParam int year) {
        return incomeService.getIncomeByMonth(userId,month,year)
                .stream()
                .map(incomeMapper::toDTO)
                .toList();
    }
}

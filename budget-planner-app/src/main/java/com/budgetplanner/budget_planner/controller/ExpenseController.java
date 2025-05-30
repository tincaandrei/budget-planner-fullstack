package com.budgetplanner.budget_planner.controller;

import com.budgetplanner.budget_planner.DTOs.ExpenseDTO;
import com.budgetplanner.budget_planner.exceptions.ExpenseNotFoundException;
import com.budgetplanner.budget_planner.exceptions.InvalidExpenseException;
import com.budgetplanner.budget_planner.exceptions.InvalidIncomeException;
import com.budgetplanner.budget_planner.mappers.ExpenseMapper;
import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.model.Category;
import com.budgetplanner.budget_planner.model.Expense;
import com.budgetplanner.budget_planner.repository.BudgetRepository;
import com.budgetplanner.budget_planner.service.BudgetService;
import com.budgetplanner.budget_planner.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseMapper  expenseMapper;
    private final BudgetRepository budgetRepository;

    public ExpenseController(ExpenseService expenseService, ExpenseMapper expenseMapper, BudgetRepository budgetRepository) {
        this.expenseService  = expenseService;
        this.expenseMapper   = expenseMapper;
        this.budgetRepository = budgetRepository;
    }

    // GET all expenses
    @GetMapping
    public List<ExpenseDTO> getAllExpenses() {
        return expenseService.getAllExpenses()
                .stream()
                .map(expenseMapper::toDTO)
                .toList();
    }

    // GET one expense by ID
    @GetMapping("/{id}")
    public ExpenseDTO getExpenseById(@PathVariable Long id) {
        Expense exp = expenseService.getExpenseById(id);
        if (exp == null) throw new ExpenseNotFoundException(id);
        return expenseMapper.toDTO(exp);
    }

    // POST a new expense
    @PostMapping
    public ExpenseDTO saveExpense(@RequestBody ExpenseDTO dto) {
        if (dto.getBudgetId() == null)
            throw new InvalidExpenseException("Budget id is required");

        Budget budget = budgetRepository.findById(dto.getBudgetId())
                .orElseThrow(() -> new InvalidExpenseException("Budget id not found for this expense"));

        Category category = new Category();
        category.setId(dto.getCategoryId());

        Expense saved = expenseService.addExpense(
                expenseMapper.toEntity(dto, budget, category)
        );
        return expenseMapper.toDTO(saved);
    }

    // DELETE an expense
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        Expense exp = expenseService.getExpenseById(id);
        if (exp == null) throw new ExpenseNotFoundException(id);
        expenseService.deleteExpense(id);
    }

    // GET expenses by date
    @GetMapping("/user/{userId}/month")
    public List<ExpenseDTO> getMonthlyExpenses(@PathVariable Long userId,
                                               @RequestParam int month,
                                               @RequestParam int year) {
        return expenseService.getExpensesByMonth(userId, month, year)
                .stream()
                .map(expenseMapper::toDTO)
                .toList();
    }
}

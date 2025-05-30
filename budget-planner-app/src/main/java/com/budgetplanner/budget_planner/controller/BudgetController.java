package com.budgetplanner.budget_planner.controller;


import com.budgetplanner.budget_planner.DTOs.BudgetDTO;
import com.budgetplanner.budget_planner.mappers.BudgetMapper;
import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.service.BudgetService;
import com.budgetplanner.budget_planner.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/budgets")
public class BudgetController {


    private final BudgetService budgetService;
    private final BudgetMapper budgetMapper;
    private final UserService userService;

    public BudgetController(BudgetService budgetService, BudgetMapper budgetMapper, UserService userService) {
        this.budgetService = budgetService;
        this.budgetMapper = budgetMapper;
        this.userService = userService;
    }



    @GetMapping
    public List<BudgetDTO> getAllBudgets() {
        return budgetService.getAllBudgets()
                .stream()
                .map(budgetMapper::toDTO)
                .toList();
    }


    @PostMapping
    public ResponseEntity saveBudget(@RequestBody BudgetDTO budgetDTO) {
        Budget budget = budgetMapper.toEntity(budgetDTO);
        Budget saved = budgetService.addBudget(budget);
        return ResponseEntity.status(HttpStatus.CREATED).body(budgetMapper.toDTO(saved));

    }


    @GetMapping("/{id}")
    public ResponseEntity getBudgetById(@PathVariable Long id) {
        Budget budget = budgetService.getBudgetById(id);
        return ResponseEntity.ok(budgetMapper.toDTO(budget));
    }




    @GetMapping("/user/{userId}")

    public ResponseEntity getBudgetsByUser(@PathVariable Long userId) {

        // an exception will be forced because of the service getuserbyid method
        userService.getUserById(userId);

        List<BudgetDTO> userBudgets = budgetService.getBudgetsByUserId(userId)
                    .stream()
                    .map(budgetMapper::toDTO)
                    .toList();
        // triggering an exception if the user has no budgets
        if (userBudgets.isEmpty()) {
            throw new NoSuchElementException("No budgets found for user with ID " + userId);
        }
        return ResponseEntity.ok(userBudgets);

    }

}

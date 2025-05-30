package com.budgetplanner.budget_planner.repository;

import com.budgetplanner.budget_planner.model.Budget;

import com.budgetplanner.budget_planner.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByBudget_Id(Long budgetId); // incomes under a budget



    @Query("SELECT i FROM Income i WHERE MONTH(i.date) = :month AND YEAR(i.date) = :year AND i.budget.user.id = :userId")
    List<Income> findByUserAndMonth(@Param("userId") Long userId,
                                     @Param("month") int month,
                                     @Param("year") int year);
}


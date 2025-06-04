package com.budgetplanner.budget_planner.repository;

import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {


    @Query("SELECT e FROM Expense e WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year AND e.budget.user.id = :userId")
    List<Expense> findByUserAndMonth(@Param("userId") Long userId,
                                     @Param("month") int month,
                                     @Param("year") int year);



    @Query("SELECT e FROM Expense e WHERE e.budget.user.id = :userId")
    List<Expense> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT e.category.name, SUM(e.amount) " +
            "FROM Expense e " +
            "WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year AND e.budget.user.id = :userId " +
            "GROUP BY e.category.name")
    List<Object[]> getCategoryExpenseTotals(@Param("userId") Long userId,
                                            @Param("month") int month,
                                            @Param("year") int year);


}

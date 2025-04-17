package com.budgetplanner.budget_planner.model;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Budget> budgets;

}

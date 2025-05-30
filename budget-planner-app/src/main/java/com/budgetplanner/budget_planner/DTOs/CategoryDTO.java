package com.budgetplanner.budget_planner.DTOs;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDTO {
    private Long id;
    private String name;
    private String type;
}

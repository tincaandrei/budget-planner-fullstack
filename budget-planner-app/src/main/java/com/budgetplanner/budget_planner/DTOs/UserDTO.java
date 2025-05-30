package com.budgetplanner.budget_planner.DTOs;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private String email;

}

package com.budgetplanner.budget_planner.model;

import lombok.*;

import java.time.LocalDate;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Income {
    private Long id;
    private Double amount;
    private LocalDate date;
    private String description;
    //foreign key for the respective budget
    private Long budgetId;
    // foreign key for the respective category
    private Category category;
}

package com.budgetplanner.budget_planner.restrictions;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PositiveAmountValidator.class)
public @interface PositiveAmount {
    String message() default "Amount must be positive";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
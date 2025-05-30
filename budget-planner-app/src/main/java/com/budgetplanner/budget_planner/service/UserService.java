package com.budgetplanner.budget_planner.service;

import com.budgetplanner.budget_planner.model.Budget;
import com.budgetplanner.budget_planner.model.User;
import com.budgetplanner.budget_planner.repository.BudgetRepository;
import com.budgetplanner.budget_planner.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;
    public UserService(UserRepository userRepository, BudgetRepository budgetRepository) {
        this.userRepository = userRepository;
        this.budgetRepository = budgetRepository;
    }

    public User addUser(User user) {
        User newUser = userRepository.save(user);
        Budget newBudget = new Budget();
        newBudget.setUser(newUser);
        newBudget.setName(newUser.getName() + " Budget");
        newBudget.setTotalAmount(0.0);
        budgetRepository.save(newBudget);
        return newUser;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " was not found"));
    }

    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new NoSuchElementException("User with id " + user.getId() + " was not found");
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

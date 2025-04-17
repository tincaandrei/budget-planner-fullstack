package com.budgetplanner.budget_planner.service;

import com.budgetplanner.budget_planner.model.User;
import com.budgetplanner.budget_planner.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id);
        if (user != null) {
            return user;
        } else {
            throw new NoSuchElementException("User with id " + id + " was not found");
        }
    }


    public User updateUser(User user) {
        return userRepository.update(user);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

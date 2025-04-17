package com.budgetplanner.budget_planner.controller;


import org.springframework.ui.Model;
import com.budgetplanner.budget_planner.model.User;
import com.budgetplanner.budget_planner.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    // Display users
    @GetMapping
    public String listUsers(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/list-users";
    }
    //  Show a form to create a new user
    @GetMapping("/new")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/new-user";
    }

    // Handle form submission to create a user
    @PostMapping
    public String saveUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/users";
    }

    // Handle delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    // Handle edit user
    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User existingUser = userService.getUserById(id);
        model.addAttribute("user", existingUser);
        return "users/edit-user";
    }


    @PostMapping("edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User userForm) {
        userForm.setId(id);
        userService.updateUser(userForm);
        return "redirect:/users";
    }

}

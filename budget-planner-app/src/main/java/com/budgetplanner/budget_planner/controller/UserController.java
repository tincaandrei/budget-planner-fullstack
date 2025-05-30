package com.budgetplanner.budget_planner.controller;

import com.budgetplanner.budget_planner.DTOs.UserDTO;
import com.budgetplanner.budget_planner.mappers.UserMapper;
import com.budgetplanner.budget_planner.model.User;
import com.budgetplanner.budget_planner.service.BudgetService;
import com.budgetplanner.budget_planner.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final BudgetService budgetService;

    public UserController(UserService userService, UserMapper userMapper, BudgetService budgetService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.budgetService = budgetService;
    }


    // GET all users
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    // GET a user by ID
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            UserDTO dto = userMapper.toDTO(user);

            return ResponseEntity.ok(dto);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }


    // POST a new user
    @PostMapping
    public ResponseEntity saveUser(@RequestBody UserDTO userDto) {
        try{
            User savedUser = userService.addUser(userMapper.toEntity(userDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDTO(savedUser));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Unable to create user due to an error:" + ex.getMessage());
        }

    }


    // PUT to update an existing user
    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestParam Long id, @RequestBody UserDTO userDto) {
        try {
            User user = userMapper.toEntity(userDto);
            user.setId(id);
            User updated = userService.updateUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDTO(updated));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("User with ID " + id + " not found.");
        }
    }

    // DELETE a user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                userService.deleteUser(id);
                return ResponseEntity.ok("User with ID " + id + " deleted.");
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot find user");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
    }

}

package com.budgetplanner.budget_planner.mappers;

import com.budgetplanner.budget_planner.DTOs.UserDTO;
import com.budgetplanner.budget_planner.model.User;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }
}

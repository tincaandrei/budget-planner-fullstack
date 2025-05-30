package com.login.mapper;

import com.login.dto.AuthDTO;
import com.login.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public AuthDTO toDTO(User user) {
        AuthDTO dto = new AuthDTO();
        dto.setUsername(user.getEmail());
        dto.setPassword(user.getPassword(  )); // optional: omit for security
        return dto;
    }

    public User toEntity(AuthDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }
}

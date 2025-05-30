package com.login.service.impl;

import com.login.dto.AuthDTO;
import com.login.model.User;
import com.login.repository.UserRepository;
import com.login.service.AuthService;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(AuthDTO auth) {
        Optional<User> userOpt = userRepository.findFirstByUsername(auth.getUsername());
        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(auth.getPassword())) {
            throw new NoSuchElementException("Invalid credentials");
        }
        return userOpt.get();
    }
}

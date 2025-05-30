package com.login.controller;

import com.login.dto.AuthDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.login.service.AuthService;

import java.util.NoSuchElementException;

@RestController

@CrossOrigin
@RequestMapping("/login")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody AuthDTO auth) throws NoSuchElementException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authService.login(auth));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

}
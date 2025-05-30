package com.login.service;
import com.login.dto.AuthDTO;
import com.login.model.User;

public interface AuthService {
    User login(AuthDTO auth);
}

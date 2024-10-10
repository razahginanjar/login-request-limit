package com.example.Login.services;


import com.example.Login.dto.requests.LoginRequest;
import com.example.Login.dto.requests.RegisterRequest;
import com.example.Login.dto.responses.LoginResponse;
import com.example.Login.dto.responses.RegisterResponse;

public interface AuthService {
    LoginResponse login(LoginRequest login);

    RegisterResponse register(RegisterRequest request);
}

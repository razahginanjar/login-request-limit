package com.example.Login.services;


import com.example.Login.dto.responses.JWTClaims;
import com.example.Login.entities.Users;

public interface JwtService {
    String generateToken(Users userAccount);
    Boolean verifyToken(String token);
    JWTClaims claimToken(String token);
}

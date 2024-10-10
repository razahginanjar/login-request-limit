package com.example.Login.services;

import com.example.Login.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
    Users getById(Integer id);
}

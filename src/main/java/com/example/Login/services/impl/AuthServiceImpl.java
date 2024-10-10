package com.example.Login.services.impl;


import com.example.Login.dto.requests.LoginRequest;
import com.example.Login.dto.requests.RegisterRequest;
import com.example.Login.dto.responses.LoginResponse;
import com.example.Login.dto.responses.RegisterResponse;
import com.example.Login.entities.Users;
import com.example.Login.repositories.UsersRepository;
import com.example.Login.services.AuthService;
import com.example.Login.services.JwtService;
import com.example.Login.utils.ValidationUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UsersRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ValidationUtils validation;

    @Value("${login_with-redis.superadmin.username}")
    private String superAdminUsername;
    @Value("${login_with-redis.superadmin.password}")
    private String superAdminPassword;

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) throws DataIntegrityViolationException {
        validation.validate(request);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authenticated = authenticationManager.authenticate(authentication);
        Users account = (Users) authenticated.getPrincipal();
        String token = jwtService.generateToken(account);
        return LoginResponse.builder()
                .token(token)
                .username(account.getUsername())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(RegisterRequest request) {
        validation.validate(request);
        String hashPassword = passwordEncoder.encode(request.getPassword());
        Users user = Users.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .email(request.getEmail())
                .name(request.getName())
                .build();
        userRepository.saveAndFlush(user);

        return RegisterResponse.builder()
                .username(user.getUsername())
                .build();
    }
}

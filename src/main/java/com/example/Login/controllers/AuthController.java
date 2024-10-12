package com.example.Login.controllers;

import com.example.Login.constants.URLs;
import com.example.Login.dto.requests.LoginRequest;
import com.example.Login.dto.requests.RegisterRequest;
import com.example.Login.dto.responses.CommonResponse;
import com.example.Login.dto.responses.LoginResponse;
import com.example.Login.dto.responses.RegisterResponse;
import com.example.Login.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = URLs.AUTH)
@Tag(name = "Authentication")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(
            description = "Register new merchant (ADMIN PRIVILEGE)",
            summary = "Register new merchant"
    )
    @PostMapping(
            path = URLs.REGISTER,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> register(@RequestBody RegisterRequest request) {
        RegisterResponse register = authService.register(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(register)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            description = "Login",
            summary = "Login"
    )
    @PostMapping(
            path = URLs.LOGIN,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> login(@RequestBody LoginRequest request) {
        LoginResponse login = authService.login(request);
        CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase() + ". Login success")
                .data(login)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

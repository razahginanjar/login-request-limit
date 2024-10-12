package com.example.Login.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  LoginRequest {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
}

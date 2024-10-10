package com.example.Login.dto.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JWTClaims {
    private Integer idUser;
}

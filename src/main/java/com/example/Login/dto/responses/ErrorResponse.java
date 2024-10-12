package com.example.Login.dto.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private String message;
    private int status;
    private long timestamp;
}

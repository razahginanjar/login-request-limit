package com.example.Login.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterVendorRequest {
    @NotNull
    @NotBlank
    private String vendorName;
}

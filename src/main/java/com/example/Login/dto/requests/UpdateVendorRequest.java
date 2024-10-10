package com.example.Login.dto.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateVendorRequest {
    private Integer vendorId;
    private String vendorName;
}

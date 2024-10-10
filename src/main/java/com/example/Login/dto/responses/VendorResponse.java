package com.example.Login.dto.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorResponse {
    private String vendorName;
    private Integer vendorId;
}

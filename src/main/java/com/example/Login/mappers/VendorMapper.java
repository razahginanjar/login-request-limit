package com.example.Login.mappers;

import com.example.Login.dto.responses.VendorResponse;
import com.example.Login.entities.Vendors;

public interface VendorMapper {
    VendorResponse toResponse(Vendors vendor);
}

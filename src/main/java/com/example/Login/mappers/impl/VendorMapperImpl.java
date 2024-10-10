package com.example.Login.mappers.impl;

import com.example.Login.dto.responses.VendorResponse;
import com.example.Login.entities.Vendors;
import com.example.Login.mappers.VendorMapper;
import org.springframework.stereotype.Component;

@Component
public class VendorMapperImpl implements VendorMapper {
    @Override
    public VendorResponse toResponse(Vendors vendor) {
        return VendorResponse.builder()
                .vendorId(vendor.getVendorId())
                .vendorName(vendor.getVendorName())
                .build();
    }
}

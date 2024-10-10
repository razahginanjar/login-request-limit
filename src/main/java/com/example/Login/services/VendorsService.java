package com.example.Login.services;

import com.example.Login.dto.requests.RegisterVendorRequest;
import com.example.Login.dto.requests.UpdateVendorRequest;
import com.example.Login.dto.responses.VendorResponse;
import com.example.Login.entities.Vendors;

import java.util.List;

public interface VendorsService {
    VendorResponse getByIdResponse(int id);
    Vendors getById(int id);
    VendorResponse update(UpdateVendorRequest updateVendor);
    void deleteById(int id);
    VendorResponse register(RegisterVendorRequest registerVendor);
    List<VendorResponse> getAllVendors();
}

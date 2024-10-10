package com.example.Login.services.impl;

import com.example.Login.dto.requests.RegisterVendorRequest;
import com.example.Login.dto.requests.UpdateVendorRequest;
import com.example.Login.dto.responses.VendorResponse;
import com.example.Login.entities.Vendors;
import com.example.Login.mappers.VendorMapper;
import com.example.Login.repositories.VendorsRepository;
import com.example.Login.services.VendorsService;
import com.example.Login.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorsService {

    private final VendorsRepository vendorsRepository;
    private final VendorMapper vendorMapper;
    private final ValidationUtils validationUtils;

    @Transactional(readOnly = true)
    @Override
    public VendorResponse getByIdResponse(int id) {
        Vendors byId = getById(id);
        return vendorMapper.toResponse(byId);
    }

    @Transactional(readOnly = true)
    @Override
    public Vendors getById(int id) {
        return vendorsRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase())
        );
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public VendorResponse update(UpdateVendorRequest updateVendor) {
        validationUtils.validate(updateVendor);
        Vendors byId = getById(updateVendor.getVendorId());
        byId.setVendorName(updateVendor.getVendorName());
        return vendorMapper.toResponse(vendorsRepository.saveAndFlush(byId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(int id) {
        Vendors byId = getById(id);
        vendorsRepository.delete(byId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public VendorResponse register(RegisterVendorRequest registerVendor) {
        validationUtils.validate(registerVendor);
        Vendors build = Vendors.builder().vendorName(registerVendor.getVendorName()).build();
        return vendorMapper.toResponse(vendorsRepository.saveAndFlush(build));
    }

    @Transactional(readOnly = true)
    @Override
    public List<VendorResponse> getAllVendors() {
        List<Vendors> all = vendorsRepository.findAll();
        return all.stream().map(
                vendorMapper::toResponse
        ).toList();
    }
}

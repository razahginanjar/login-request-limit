package com.example.Login.controllers;

import com.example.Login.constants.URLs;
import com.example.Login.dto.requests.RegisterVendorRequest;
import com.example.Login.dto.requests.UpdateVendorRequest;
import com.example.Login.dto.responses.CommonResponse;
import com.example.Login.dto.responses.VendorResponse;
import com.example.Login.services.VendorsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = URLs.VENDOR)
@RequiredArgsConstructor
@Tag(name = "Vendor Control")
@SecurityRequirement(name = "bearerAuth")
public class VendorController {
    private final VendorsService vendorsService;

    @Operation(
            description = "Register new Vendor",
            summary = "Register new Vendor"
    )
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> registerVendor(@RequestBody RegisterVendorRequest registerVendorRequest)
    {
        VendorResponse register = vendorsService.register(registerVendorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.builder()
                        .data(register)
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created vendor!")
                        .build()
        );
    }

    @Operation(
            description = "Get a Specific Vendor",
            summary = "Get a Specific Vendor"
    )
    @GetMapping(
            path = URLs.ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> getVendor(@PathVariable(name = "id") int id)
    {
        VendorResponse register = vendorsService.getByIdResponse(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .data(register)
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get a vendor!")
                        .build()
        );
    }

    @Operation(
            description = "Get Vendors",
            summary = "Get Vendors"
    )
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> getVendors()
    {
        List<VendorResponse> allVendors = vendorsService.getAllVendors();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .data(allVendors)
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get vendors!")
                        .build()
        );
    }

    @Operation(
            description = "Update Specific Vendor",
            summary = "Update Specific Vendor"
    )
    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> updateVendor(@RequestBody UpdateVendorRequest updateVendorRequest)
    {
        VendorResponse update = vendorsService.update(updateVendorRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .data(update)
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update a vendor!")
                        .build()
        );
    }

    @Operation(
            description = "Delete Specific Vendor",
            summary = "Delete Specific Vendor"
    )
    @DeleteMapping(
            path = URLs.ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<?>> deleteVendor(@PathVariable(name = "id") int id)
    {
        vendorsService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .data("deleted vendor with id " + id)
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete a vendor!")
                        .build()
        );
    }
}

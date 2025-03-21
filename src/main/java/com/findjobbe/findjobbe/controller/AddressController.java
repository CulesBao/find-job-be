package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.mapper.response.AbstractResponse;
import com.findjobbe.findjobbe.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AddressController {
  private final IAddressService addressService;

  @Autowired
  public AddressController(IAddressService addressService) {
    this.addressService = addressService;
  }

  @GetMapping("/provinces")
  public ResponseEntity<?> getProvices() {
    return ResponseEntity.ok(
        new AbstractResponse("Get provices successfully", addressService.getAllProvinces()));
  }

  @GetMapping("/districts/{provinceCode}")
  public ResponseEntity<?> getDistrictsByProvinceCode(@PathVariable String provinceCode) {
    return ResponseEntity.ok(
        new AbstractResponse(
            "Get districts successfully", addressService.getDistrictsByProvinceCode(provinceCode)));
  }
}

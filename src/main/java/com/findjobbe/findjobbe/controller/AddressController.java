package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.mapper.response.AbstractResponse;
import com.findjobbe.findjobbe.mapper.response.ProvinceResponse;
import com.findjobbe.findjobbe.service.IAddressService;
import java.util.Optional;
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
        new AbstractResponse(
            "Get provices successfully",
            Optional.of(new ProvinceResponse(addressService.getAllProvinces()))));
  }

  @GetMapping("/districts/{provinceCode}")
  public ResponseEntity<?> getDistrictsByProvinceCode(@PathVariable String provinceCode) {
    return ResponseEntity.ok(
        new AbstractResponse(
            "Get districts successfully",
            Optional.of(addressService.getDistrictsByProvinceCode(provinceCode))));
  }
}

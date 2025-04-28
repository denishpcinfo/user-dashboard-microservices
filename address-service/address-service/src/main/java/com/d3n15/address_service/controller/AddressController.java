package com.d3n15.address_service.controller;

import com.d3n15.address_service.dto.AddressDTO;
import com.d3n15.address_service.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{userId}")
    public AddressDTO getAddress(@PathVariable String userId) {
        return addressService.getAddressByUserId(userId);
    }
}
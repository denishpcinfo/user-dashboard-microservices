package com.d3n15.address_service.service;

import com.d3n15.address_service.dto.AddressDTO;
import com.d3n15.address_service.entity.AddressEntity;
import com.d3n15.address_service.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressDTO getAddressByUserId(String userId) {
        Optional<AddressEntity> optionalAddress = addressRepository.findById(userId);
        if (optionalAddress.isEmpty()) {
            throw new RuntimeException("Endereço não encontrado para usuário: " + userId);
        }

        AddressEntity address = optionalAddress.get();
        return new AddressDTO(address.getStreet(), address.getHouseNumber(), address.getCity(), address.getState(), address.getZipCode());
    }
}
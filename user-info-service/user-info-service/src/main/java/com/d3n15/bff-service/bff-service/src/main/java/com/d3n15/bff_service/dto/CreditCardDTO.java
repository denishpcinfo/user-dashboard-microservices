package com.d3n15.bff_service.dto;

import java.io.Serializable;

public class CreditCardDTO implements Serializable {

    private String brand;
    private String maskedNumber;

    public CreditCardDTO() {
    }

    public CreditCardDTO(String brand, String maskedNumber) {
        this.brand = brand;
        this.maskedNumber = maskedNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMaskedNumber() {
        return maskedNumber;
    }

    public void setMaskedNumber(String maskedNumber) {
        this.maskedNumber = maskedNumber;
    }
}
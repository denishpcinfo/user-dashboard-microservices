package com.d3n15.credit_card_service.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "credit_cards")
public class CreditCardEntity {

    @Id
    private String id;
    private String brand;
    private String maskedNumber;
    private String userId;

    public CreditCardEntity() {}

    public CreditCardEntity(String id, String brand, String maskedNumber, String userId) {
        this.id = id;
        this.brand = brand;
        this.maskedNumber = maskedNumber;
        this.userId = userId;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getBrand() { return brand; }

    public void setBrand(String brand) { this.brand = brand; }

    public String getMaskedNumber() { return maskedNumber; }

    public void setMaskedNumber(String maskedNumber) { this.maskedNumber = maskedNumber; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "CreditCardEntity{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", maskedNumber='" + maskedNumber + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
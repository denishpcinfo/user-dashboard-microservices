package com.d3n15.address_service.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class AddressEntity {

    @Id
    private String id;
    private String street;
    private String houseNumber;
    private String city;
    private String state;
    private String zipCode;

    public AddressEntity() {}

    public AddressEntity(String id, String street, String houseNumber, String city, String state, String zipCode) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getStreet() { return street; }

    public void setStreet(String street) { this.street = street; }

    public String getHouseNumber() { return houseNumber; }

    public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }

    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "id='" + id + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
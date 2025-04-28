package com.d3n15.bff_service.dto;

import java.io.Serializable;

public class UserInfoDTO implements Serializable {

    private String name;
    private String cpf;
    private String phone;

    public UserInfoDTO() {
    }

    public UserInfoDTO(String name, String cpf, String phone) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

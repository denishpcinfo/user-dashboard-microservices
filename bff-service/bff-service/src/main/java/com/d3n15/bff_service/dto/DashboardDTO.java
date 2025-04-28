package com.d3n15.bff_service.dto;

import java.io.Serializable;
import java.util.List;

public class DashboardDTO implements Serializable {

    private UserInfoDTO userInfo;
    private AddressDTO address;
    private List<CreditCardDTO> creditCards;
    private List<InvoiceDTO> invoices;

    public DashboardDTO() {
    }

    public DashboardDTO(UserInfoDTO userInfo, AddressDTO address, List<CreditCardDTO> creditCards, List<InvoiceDTO> invoices) {
        this.userInfo = userInfo;
        this.address = address;
        this.creditCards = creditCards;
        this.invoices = invoices;
    }

    public UserInfoDTO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDTO userInfo) {
        this.userInfo = userInfo;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<CreditCardDTO> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCardDTO> creditCards) {
        this.creditCards = creditCards;
    }

    public List<InvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceDTO> invoices) {
        this.invoices = invoices;
    }
}
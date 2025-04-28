package com.d3n15.bff_service.dto;

import java.io.Serializable;
import java.util.List;

public class CardsAndInvoicesDTO implements Serializable {

    private List<CreditCardDTO> creditCards;
    private List<InvoiceDTO> invoices;

    public CardsAndInvoicesDTO() {
    }

    public CardsAndInvoicesDTO(List<CreditCardDTO> creditCards, List<InvoiceDTO> invoices) {
        this.creditCards = creditCards;
        this.invoices = invoices;
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
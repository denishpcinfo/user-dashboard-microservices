package com.d3n15.bff_service.dto;

import java.io.Serializable;

public class InvoiceDTO implements Serializable {

    private String dueDate;
    private double amount;

    public InvoiceDTO() {
    }

    public InvoiceDTO(String dueDate, double amount) {
        this.dueDate = dueDate;
        this.amount = amount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
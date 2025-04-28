package com.d3n15.invoice_service.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invoices")
public class InvoiceEntity {

    @Id
    private String id;
    private String cardId;
    private String dueDate;
    private double amount;

    public InvoiceEntity() {}

    public InvoiceEntity(String id, String cardId, String dueDate, double amount) {
        this.id = id;
        this.cardId = cardId;
        this.dueDate = dueDate;
        this.amount = amount;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getCardId() { return cardId; }

    public void setCardId(String cardId) { this.cardId = cardId; }

    public String getDueDate() { return dueDate; }

    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public double getAmount() { return amount; }

    public void setAmount(double amount) { this.amount = amount; }

    @Override
    public String toString() {
        return "InvoiceEntity{" +
                "id='" + id + '\'' +
                ", cardId='" + cardId + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", amount=" + amount +
                '}';
    }
}
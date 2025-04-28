package com.d3n15.invoice_service.controller;

import com.d3n15.invoice_service.dto.InvoiceDTO;
import com.d3n15.invoice_service.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/{cardId}")
    public List<InvoiceDTO> getInvoices(@PathVariable String cardId) {
        return invoiceService.getInvoicesByCardId(cardId);
    }
}
package com.d3n15.invoice_service.service;

import com.d3n15.invoice_service.dto.InvoiceDTO;
import com.d3n15.invoice_service.entity.InvoiceEntity;
import com.d3n15.invoice_service.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<InvoiceDTO> getInvoicesByCardId(String cardId) {
        List<InvoiceEntity> invoices = invoiceRepository.findByCardId(cardId);
        return invoices.stream()
                .map(invoice -> new InvoiceDTO(invoice.getDueDate(), invoice.getAmount()))
                .collect(Collectors.toList());
    }
}
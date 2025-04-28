package com.d3n15.invoice_service.repository;

import com.d3n15.invoice_service.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, String> {
    List<InvoiceEntity> findByCardId(String cardId);
}
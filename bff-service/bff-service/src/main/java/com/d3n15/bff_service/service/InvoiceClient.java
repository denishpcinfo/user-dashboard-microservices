package com.d3n15.bff_service.service;

import com.d3n15.bff_service.dto.CreditCardDTO;
import com.d3n15.bff_service.dto.CardsAndInvoicesDTO;
import com.d3n15.bff_service.dto.InvoiceDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceClient {

    private final WebClient webClient;

    public InvoiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<CardsAndInvoicesDTO> getInvoicesForCards(List<CreditCardDTO> cards) {
        List<Mono<List<InvoiceDTO>>> invoicesMonos = cards.stream()
                .map(card -> getInvoices(card.getMaskedNumber()))
                .collect(Collectors.toList());

        return Mono.zip(invoicesMonos, results -> {
            List<InvoiceDTO> invoices = List.of();
            for (Object obj : results) {
                invoices.addAll((List<InvoiceDTO>) obj);
            }
            return new CardsAndInvoicesDTO(cards, invoices);
        });
    }

    @Retry(name = "invoiceService")
    @CircuitBreaker(name = "invoiceService", fallbackMethod = "fallback")
    public Mono<List<InvoiceDTO>> getInvoices(String cardId) {
        return webClient.get()
                .uri("http://localhost:8084/invoices/{cardId}", cardId)
                .retrieve()
                .bodyToFlux(InvoiceDTO.class)
                .collectList();
    }

    private Mono<List<InvoiceDTO>> fallback(String cardId, Throwable t) {
        return Mono.just(List.of());
    }
}

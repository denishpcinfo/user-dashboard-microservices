package com.d3n15.bff_service.service;

import com.d3n15.bff_service.dto.CreditCardDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class CreditCardClient {

    private final WebClient webClient;

    public CreditCardClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Retry(name = "creditCardService")
    @CircuitBreaker(name = "creditCardService", fallbackMethod = "fallback")
    public Mono<List<CreditCardDTO>> getCreditCards(String userId) {
        return webClient.get()
                .uri("http://localhost:8083/cards/{userId}", userId)
                .retrieve()
                .bodyToFlux(CreditCardDTO.class)
                .collectList();
    }

    private Mono<List<CreditCardDTO>> fallback(String userId, Throwable t) {
        return Mono.just(List.of());
    }
}
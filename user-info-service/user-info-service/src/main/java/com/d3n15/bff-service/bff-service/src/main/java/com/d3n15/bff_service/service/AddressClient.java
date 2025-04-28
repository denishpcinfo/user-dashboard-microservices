package com.d3n15.bff_service.service;

import com.d3n15.bff_service.dto.AddressDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AddressClient {

    private final WebClient webClient;

    public AddressClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Retry(name = "addressService")
    @CircuitBreaker(name = "addressService", fallbackMethod = "fallback")
    public Mono<AddressDTO> getAddress(String userId) {
        return webClient.get()
                .uri("http://localhost:8082/address/{userId}", userId)
                .retrieve()
                .bodyToMono(AddressDTO.class);
    }

    private Mono<AddressDTO> fallback(String userId, Throwable t) {
        return Mono.error(new RuntimeException("Address info unavailable", t));
    }
}

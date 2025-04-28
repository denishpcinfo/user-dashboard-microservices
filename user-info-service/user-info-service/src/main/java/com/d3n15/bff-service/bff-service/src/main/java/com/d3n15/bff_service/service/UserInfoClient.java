package com.d3n15.bff_service.service;

import com.d3n15.bff_service.dto.UserInfoDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserInfoClient {

    private final WebClient webClient;

    public UserInfoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Retry(name = "userInfoService")
    @CircuitBreaker(name = "userInfoService", fallbackMethod = "fallback")
    public Mono<UserInfoDTO> getUserInfo(String userId) {
        return webClient.get()
                .uri("http://localhost:8081/user/{userId}", userId)
                .retrieve()
                .bodyToMono(UserInfoDTO.class);
    }

    private Mono<UserInfoDTO> fallback(String userId, Throwable t) {
        return Mono.error(new RuntimeException("User info unavailable", t));
    }
}

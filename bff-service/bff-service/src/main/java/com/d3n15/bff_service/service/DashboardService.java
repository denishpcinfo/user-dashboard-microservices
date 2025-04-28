package com.d3n15.bff_service.service;

import com.d3n15.bff_service.dto.*;
import com.d3n15.bff_service.utils.JsonUtils;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final WebClient.Builder webClientBuilder;
    private final ReactiveValueOperations<String, String> redisOps;
    private final Retry retry;

    @Value("${spring.redis.cache.user-ttl:300}")
    private long userTtl;

    @Value("${spring.redis.cache.card-ttl:60}")
    private long cardTtl;

    public DashboardService(WebClient.Builder webClientBuilder, ReactiveStringRedisTemplate redisTemplate, Retry retry) {
        this.webClientBuilder = webClientBuilder;
        this.redisOps = redisTemplate.opsForValue();
        this.retry = retry;
    }

    public Mono<DashboardDTO> getDashboard(String userId) {
        return Mono.zip(
                getUserInfo(userId),
                getAddress(userId),
                getCreditCards(userId).flatMap(this::getInvoicesForCards)
        ).map(tuple -> {
            CardsAndInvoicesDTO cardsAndInvoices = tuple.getT3();
            return new DashboardDTO(
                    tuple.getT1(),                     // UserInfoDTO
                    tuple.getT2(),                     // AddressDTO
                    cardsAndInvoices.getCreditCards(), // List<CreditCardDTO>
                    cardsAndInvoices.getInvoices()     // List<InvoiceDTO>
            );
        });
    }

    private Mono<UserInfoDTO> getUserInfo(String userId) {
        String cacheKey = "user:" + userId + ":info";

        return redisOps.get(cacheKey)
                .flatMap(json -> Mono.just(JsonUtils.fromJson(json, UserInfoDTO.class)))
                .switchIfEmpty(
                        callMicroservice("/user/" + userId, "http://localhost:8081", UserInfoDTO.class)
                                .flatMap(userInfo -> redisOps.set(cacheKey, JsonUtils.toJson(userInfo), Duration.ofSeconds(userTtl)).thenReturn(userInfo))
                );
    }

    private Mono<AddressDTO> getAddress(String userId) {
        String cacheKey = "user:" + userId + ":address";

        return redisOps.get(cacheKey)
                .flatMap(json -> Mono.just(JsonUtils.fromJson(json, AddressDTO.class)))
                .switchIfEmpty(
                        callMicroservice("/address/" + userId, "http://localhost:8082", AddressDTO.class)
                                .flatMap(address -> redisOps.set(cacheKey, JsonUtils.toJson(address), Duration.ofSeconds(userTtl)).thenReturn(address))
                );
    }

    private Mono<List<CreditCardDTO>> getCreditCards(String userId) {
        String cacheKey = "user:" + userId + ":cards";

        return redisOps.get(cacheKey)
                .flatMap(json -> Mono.just(JsonUtils.fromJsonList(json, CreditCardDTO.class)))
                .switchIfEmpty(
                        callMicroserviceList("/cards/" + userId, "http://localhost:8083", CreditCardDTO.class)
                                .flatMap(cards -> redisOps.set(cacheKey, JsonUtils.toJson(cards), Duration.ofSeconds(cardTtl)).thenReturn(cards))
                );
    }

    private Mono<CardsAndInvoicesDTO> getInvoicesForCards(List<CreditCardDTO> cards) {
        List<Mono<List<InvoiceDTO>>> invoiceMonos = cards.stream()
                .map(card -> getInvoices(card.getMaskedNumber()))
                .collect(Collectors.toList());

        return Mono.zip(invoiceMonos, results -> {
            List<InvoiceDTO> invoices = List.of();
            for (Object obj : results) {
                invoices.addAll((List<InvoiceDTO>) obj);
            }
            return new CardsAndInvoicesDTO(cards, invoices);
        });
    }

    private Mono<List<InvoiceDTO>> getInvoices(String cardId) {
        String cacheKey = "card:" + cardId + ":invoices";

        return redisOps.get(cacheKey)
                .flatMap(json -> Mono.just(JsonUtils.fromJsonList(json, InvoiceDTO.class)))
                .switchIfEmpty(
                        callMicroserviceList("/invoices/" + cardId, "http://localhost:8084", InvoiceDTO.class)
                                .flatMap(invoices -> redisOps.set(cacheKey, JsonUtils.toJson(invoices), Duration.ofSeconds(cardTtl)).thenReturn(invoices))
                );
    }

    private <T> Mono<T> callMicroservice(String uri, String baseUrl, Class<T> clazz) {
        return Mono.defer(() -> webClientBuilder.baseUrl(baseUrl)
                        .build()
                        .get()
                        .uri(uri)
                        .retrieve()
                        .bodyToMono(clazz)
                )
                .transformDeferred(RetryOperator.of(retry))
                .onErrorResume(this::handleException);
    }

    private <T> Mono<List<T>> callMicroserviceList(String uri, String baseUrl, Class<T> clazz) {
        return Mono.defer(() -> webClientBuilder.baseUrl(baseUrl)
                        .build()
                        .get()
                        .uri(uri)
                        .retrieve()
                        .bodyToFlux(clazz)
                        .collectList()
                )
                .transformDeferred(RetryOperator.of(retry))
                .onErrorResume(this::handleExceptionList);
    }

    private <T> Mono<T> handleException(Throwable throwable) {
        if (throwable instanceof WebClientResponseException ex && (ex.getStatusCode().is5xxServerError() || ex.getStatusCode().is4xxClientError())) {
            return Mono.error(new ServiceUnavailableException("Serviço temporariamente indisponível"));
        }
        return Mono.error(new ServiceUnavailableException("Falha ao chamar o serviço"));
    }

    private <T> Mono<List<T>> handleExceptionList(Throwable throwable) {
        if (throwable instanceof WebClientResponseException ex && (ex.getStatusCode().is5xxServerError() || ex.getStatusCode().is4xxClientError())) {
            return Mono.error(new ServiceUnavailableException("Serviço temporariamente indisponível"));
        }
        return Mono.error(new ServiceUnavailableException("Falha ao chamar o serviço"));
    }

    public static class ServiceUnavailableException extends RuntimeException {
        public ServiceUnavailableException(String message) {
            super(message);
        }
    }
}

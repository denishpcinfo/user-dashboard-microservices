package com.d3n15.bff_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .filter((request, next) -> next.exchange(request)
                        .retryWhen(RetryBackoffSpec.backoff(3, Duration.ofSeconds(2))
                                .maxBackoff(Duration.ofSeconds(8))
                                .jitter(0.5))
                )
                .build();
    }
}
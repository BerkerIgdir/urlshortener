package com.urlshortener.util;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class IdGeneratorServiceCaller {
    private static final String ID_GENERATOR_URI = "http://id-generator/api/v1/getId";

    private final RestTemplate restTemplate ;
    private final ThreadLocalRandom randomGenerator= ThreadLocalRandom.current();
    public IdGeneratorServiceCaller(@Qualifier(value = "DiscoveryRestTemplate") RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "idGenServiceBreaker",fallbackMethod = "fallBackId")
    public Optional<Long> getIdFromIdService() {
        return Optional.of (restTemplate.getForEntity (ID_GENERATOR_URI, Long.class))
                .filter (resp -> HttpStatus.OK.equals (resp.getStatusCode ()))
                .map (ResponseEntity::getBody);
    }

    @Bean(name = "DiscoveryRestTemplate")
    @LoadBalanced
    public static RestTemplate restTemplateBeanProvider(){
        return new RestTemplateBuilder().setConnectTimeout(Duration.ofSeconds(7))
                                        .setReadTimeout(Duration.ofSeconds(7))
                                        .build();
    }

    private Optional<Long> fallBackId(Throwable t){
        return Optional.of(randomGenerator.nextLong());
    }
}

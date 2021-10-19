package com.urlshortener.util;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {
    private static final RestTemplate restTemplate = new RestTemplate ();
    private HttpUtil(){}
    public static long getIdFromIdService() {
       return Optional.of (restTemplate.getForEntity ("service discovery server adress comes here", Long.class))
               .filter (resp -> HttpStatus.OK.equals (resp.getStatusCode ()))
               .map (ResponseEntity::getBody)
               .orElseThrow (RuntimeException::new);
    }
}

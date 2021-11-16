package com.urlshortener.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {
    private static final RestTemplate restTemplate = new RestTemplate ();
    private HttpUtil(){}

    public static long getIdFromIdService() {
       return Optional.of (restTemplate.getForEntity ("service discovery server address comes here", Long.class))
               .filter (resp -> HttpStatus.OK.equals (resp.getStatusCode ()))
               .map (ResponseEntity::getBody)
               .orElseThrow (RuntimeException::new);
    }

    public static String getIpAddressFromRequest(HttpServletRequest request){
        return Optional.ofNullable (request.getHeader("X-Forwarded-For"))
                        .orElseGet (request::getRemoteAddr);
    }

    public static Optional<String> getCookieValueFromARequest(HttpServletRequest request, String keyToGet){

      return Optional.ofNullable (request.getCookies ())
               .stream ()
               .flatMap (Arrays::stream)
               .filter (c -> keyToGet.equalsIgnoreCase (c.getName ()))
               .findFirst ()
               .map (Cookie::getValue);
    }

    public static String userIdCookieGenerator (String ipAddress){
        return HashUtils.sha256Hasher (ipAddress)
                .map (longId -> longId & Long.MAX_VALUE)
                .map (Long::toHexString)
                .orElseThrow (RuntimeException::new);
    }

}

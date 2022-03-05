package com.urlshortener.util;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class HttpUtil {

    private HttpUtil(){}

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

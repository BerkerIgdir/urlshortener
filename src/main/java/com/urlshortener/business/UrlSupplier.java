package com.urlshortener.business;

import java.util.Optional;

@FunctionalInterface
public interface UrlSupplier {
    Optional<String> getShortenedUrl(String urlToShorten);
}

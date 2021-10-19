package com.urlshortener.dto;


public record ShortenerResponseDTO(String shortenedUrl) {

    public String getShortenedUrl () {
        return this.shortenedUrl;
    }
}

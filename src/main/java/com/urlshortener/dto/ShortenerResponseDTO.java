package com.urlshortener.dto;


public final class ShortenerResponseDTO{
    private final String shortenedUrl;
    public ShortenerResponseDTO(String shortenedUrl){
        this.shortenedUrl = shortenedUrl;
    }
    public String getShortenedUrl () {
        return this.shortenedUrl;
    }
}

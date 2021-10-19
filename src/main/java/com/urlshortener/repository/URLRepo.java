package com.urlshortener.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.urlshortener.pojo.URLInfo;

public interface URLRepo extends MongoRepository<URLInfo,Long> {
    Optional<URLInfo> findByLongUrl(String longUrl);
    Optional<URLInfo> findByShortUrl(String shortUrl);
}

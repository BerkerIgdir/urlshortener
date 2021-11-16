package com.urlshortener.repository;


import java.util.Optional;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.urlshortener.pojo.URLInfo;

@Repository
public class CacheRepo {
    private final RedisTemplate<String, URLInfo> redisTemplate;
    private final HashOperations<String,String,String> hashOperations;
    private static final String MAIN_KEY = "SESSION_ID";

    public CacheRepo (RedisTemplate <String, URLInfo> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash ();
    }

    public void saveToCache(URLInfo urlInfo){
        hashOperations.put (MAIN_KEY,urlInfo.getShortUrl (),urlInfo.getLongUrl ());
    }

    public Optional<String> getUrlFromCache (String url){
       return Optional.ofNullable (hashOperations.get (MAIN_KEY,url));
    }

}

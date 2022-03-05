package com.urlshortener.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;

import com.urlshortener.pojo.URLInfo;

@Configuration
@RedisHash
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String HOST_NAME;

    @Value ("${spring.redis.port}")
    private int PORT;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory ( new RedisStandaloneConfiguration(HOST_NAME, PORT));
    }

    @Bean
    public RedisTemplate <String, URLInfo> redisTemplate() {
        RedisTemplate<String, URLInfo> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}

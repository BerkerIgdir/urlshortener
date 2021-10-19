package com.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.urlshortener.repository")
public class UrlshortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlshortenerApplication.class, args);
	}

}

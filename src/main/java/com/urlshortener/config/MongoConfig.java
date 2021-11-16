package com.urlshortener.config;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoConfig extends AbstractMongoClientConfiguration {

    private final Environment environment;

    public MongoConfig (Environment environment) {
        this.environment = environment;
    }

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    public MongoClient mongoClient() {
        var connectionString = new ConnectionString("mongodb://mongodb1:27017,mongodb2:27017,mongodb3:27017/urldb?replicaSet=rsmongo");
        var mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.shortener");
    }
}
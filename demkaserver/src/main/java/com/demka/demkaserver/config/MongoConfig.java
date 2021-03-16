package com.demka.demkaserver.config;


import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

/**
 * This class constructs the mongo client required by the justice league module.
 *
 * @author dinuka
 *
 */
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(this.host);
    }

    @Override
    protected boolean autoIndexCreation() {
        return super.autoIndexCreation();
    }

}

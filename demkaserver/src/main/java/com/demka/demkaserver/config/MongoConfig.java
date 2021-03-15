package com.demka.demkaserver.config;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

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
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${server.dbName}")
    private String dbName;

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Override
    public Mongo mongo() throws Exception {
        String[] addresses = mongoUri.split(",");
        List<ServerAddress> servers = new ArrayList<>();
        for (String address : addresses) {
            String[] split = address.trim().split(":");
            servers.add(new ServerAddress(split[0].trim(), Integer.parseInt(split[1].trim())));
        }
        return new MongoClient(servers);
    }

}

package com.devesh.cricket.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoDBDatabaseConnection implements DatabaseConnection {

    private final String uri;
    private MongoClient mongoClient;

    public MongoDBDatabaseConnection(String uri) {
        this.uri = uri;
    }

    @Override
    public MongoClient connect() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(uri);
            System.out.println("Connected to MongoDB");
        }
        return mongoClient;
    }

    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("MongoDB connection closed.");
        }
    }
}

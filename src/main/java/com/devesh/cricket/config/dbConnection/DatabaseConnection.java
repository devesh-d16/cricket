package com.devesh.cricket.config.dbConnection;

public interface DatabaseConnection {
    Object connect();
    void close();
}

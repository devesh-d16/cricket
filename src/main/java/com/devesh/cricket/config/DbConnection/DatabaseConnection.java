package com.devesh.cricket.config.DbConnection;

public interface DatabaseConnection {
    Object connect();
    void close();
}

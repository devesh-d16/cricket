package com.devesh.cricket.config;


public interface DatabaseConnection {
    Object connect();
    void close();
}

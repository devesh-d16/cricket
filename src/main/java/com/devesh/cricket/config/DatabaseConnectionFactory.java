package com.devesh.cricket.config;

import com.mysql.cj.MysqlConnection;

import java.util.HashMap;
import java.util.Map;

public class DatabaseConnectionFactory {
    private static final Map<String, DatabaseConnection> connectionPool = new HashMap<>();

    public static DatabaseConnection getDatabaseConnection(String dbType,
                                                           String url,
                                                           String username,
                                                           String password) {
        if (connectionPool.containsKey(dbType)) {
            return connectionPool.get(dbType);
        }

        DatabaseConnection connection = switch (dbType.toLowerCase()) {
            case "mysql" -> new MySQLDatabaseConnection(url, username, password);
            case "mongodb" -> new MongoDBDatabaseConnection(url);
            default -> throw new IllegalArgumentException("Unsupported database type: " + dbType);
        };

        connectionPool.put(dbType, connection);
        return connection;
    }
}

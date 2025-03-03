package com.devesh.cricket.config.dbConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConnector {

    @Value("${spring.jpa.database}")
    private String dbType;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private DatabaseConnection databaseConnection;

    public Object connect(){
        databaseConnection = DatabaseConnectionFactory.getDatabaseConnection(dbType, url, username, password);
        return databaseConnection.connect();
    }

    public void close(){
        if(databaseConnection != null){
            databaseConnection.close();
        }
    }
}

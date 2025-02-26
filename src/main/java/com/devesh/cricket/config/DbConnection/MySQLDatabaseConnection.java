package com.devesh.cricket.config.DbConnection;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class MySQLDatabaseConnection implements DatabaseConnection {

    private final String url;
    private final String username;
    private final String password;
    private DataSource dataSource;

    public MySQLDatabaseConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public DataSource connect() {
        if(dataSource == null) {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);
            dataSource = ds;
            System.out.println("Connected to MySQL database...");
        }
        return dataSource;
    }

    @Override
    public void close() {
        System.out.println("MySQL database connection closed.");
    }
}

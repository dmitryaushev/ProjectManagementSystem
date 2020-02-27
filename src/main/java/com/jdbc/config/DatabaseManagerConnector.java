package com.jdbc.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManagerConnector {

    private final String url;

    public DatabaseManagerConnector(String hostname, int port, String database) {
        url = String.format("jdbc:postgresql://%s:%d/%s", hostname, port, database);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
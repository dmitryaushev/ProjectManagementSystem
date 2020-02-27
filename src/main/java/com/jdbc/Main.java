package com.jdbc;

import com.jdbc.config.DatabaseManagerConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        DatabaseManagerConnector dbConnector = new DatabaseManagerConnector("localhost", 5432,
                "it");
        Connection connection = dbConnector.getConnection();

    }
}

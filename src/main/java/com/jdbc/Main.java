package com.jdbc;

import com.jdbc.config.MainController;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        new MainController().run();
    }
}
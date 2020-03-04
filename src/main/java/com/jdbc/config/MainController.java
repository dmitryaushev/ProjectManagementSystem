package com.jdbc.config;

import com.jdbc.service.CompanyService;
import com.jdbc.service.DeveloperService;

import java.sql.Connection;
import java.sql.SQLException;

public class MainController {

    private View view;
    private DatabaseManagerConnector dbConnector;
    private Connection connection;

    public MainController() throws SQLException {
        view = new Console();
        dbConnector = new DatabaseManagerConnector("localhost", 5432, "it");
        connection = dbConnector.getConnection();
    }

    public void run() {

        view.write("Welcome");
        while (true) {
            view.write("Chose a command. Press Q to exit");
            String input = view.read();
            if (input.equals("Q"))
                break;
            doCommand(input);
        }
    }

    private void doCommand(String input) {


    }


}

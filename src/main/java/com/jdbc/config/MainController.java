package com.jdbc.config;

import com.jdbc.dao.CompanyDAO;
import com.jdbc.model.Company;
import com.jdbc.service.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MainController {

    private View view;
    private DatabaseManagerConnector dbConnector;
    private Connection connection;
    private List<Command> commands;
    private CompanyDAO companyDAO;

    public MainController() throws SQLException {
        view = new Console();
        dbConnector = new DatabaseManagerConnector("localhost", 5432, "it");
        connection = dbConnector.getConnection();
        companyDAO = new CompanyDAO(connection);
        commands = Arrays.asList(
                new CreateCompany(view, companyDAO),
                new DeleteCompany(view, companyDAO),
                new GetCompany(view, companyDAO),
                new GetAllCompanies(companyDAO)
        );
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

        for (Command command : commands)
            if (command.canProcess(input)) {
                command.process();
                break;
            }
    }


}

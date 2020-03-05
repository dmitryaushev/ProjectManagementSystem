package com.jdbc.config;

import com.jdbc.dao.*;
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
    private CustomerDAO customerDAO;
    private DeveloperDAO developerDAO;
    private ProjectDAO projectDAO;
    private SkillDAO skillDAO;

    public MainController() throws SQLException {

        view = new Console();

        dbConnector = new DatabaseManagerConnector("localhost", 5432, "it");
        connection = dbConnector.getConnection();

        companyDAO = new CompanyDAO(connection);
        customerDAO = new CustomerDAO(connection);
        developerDAO = new DeveloperDAO(connection);
        projectDAO = new ProjectDAO(connection);
        skillDAO = new SkillDAO(connection);

        commands = Arrays.asList(
                new CreateCompany(view, companyDAO),
                new DeleteCompany(view, companyDAO),
                new GetCompany(view, companyDAO),
                new GetAllCompanies(companyDAO),
                new CreateCustomer(view, customerDAO),
                new DeleteCustomer(view, customerDAO),
                new GetCustomer(view, customerDAO),
                new GetAllCustomers(customerDAO),
                new CreateDeveloper(view, developerDAO),
                new DeleteDeveloper(view, developerDAO),
                new GetDeveloper(view, developerDAO),
                new GetAllDevelopers(developerDAO),
                new CreateProject(view, projectDAO),
                new DeleteProject(view, projectDAO),
                new GetProject(view, projectDAO),
                new GetAllProjects(projectDAO),
                new LinkDeveloperToProject(view, developerDAO, projectDAO),
                new LinkDeveloperToSkill(view, developerDAO, skillDAO),
                new LinkProjectToCustomer(view, projectDAO, customerDAO),
                new LinkProjectToCompany(view, projectDAO, companyDAO)
        );
    }

    public void run() {

        view.write("Welcome");
        while (true) {
            view.write("Choose a command. Press Q to exit");
            commands.forEach(x -> System.out.println(x.command()));
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

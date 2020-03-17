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
                new CreateCustomer(view, customerDAO),
                new CreateDeveloper(view, developerDAO),
                new CreateProject(view, projectDAO),
                new GetCompany(view, companyDAO),
                new GetCustomer(view, customerDAO),
                new GetDeveloper(view, developerDAO),
                new GetProject(view, projectDAO),
                new GetAllCompanies(companyDAO),
                new GetAllCustomers(customerDAO),
                new GetAllDevelopers(developerDAO),
                new GetAllProjects(projectDAO),
                new UpdateCompany(view, companyDAO),
                new UpdateCustomer(view, customerDAO),
                new UpdateDeveloper(view, developerDAO),
                new UpdateProject(view, projectDAO),
                new DeleteCompany(view, companyDAO),
                new DeleteCustomer(view, customerDAO),
                new DeleteDeveloper(view, developerDAO),
                new DeleteProject(view, projectDAO),
                new LinkDeveloperToProject(view, developerDAO, projectDAO),
                new LinkDeveloperToSkill(view, developerDAO, skillDAO),
                new LinkProjectToCustomer(view, projectDAO, customerDAO),
                new LinkProjectToCompany(view, projectDAO, companyDAO),
                new GetSumSalaryByProject(view, projectDAO),
                new GetAllDevelopersByProject(view, projectDAO),
                new GetAllProjectsWithDevelopers(projectDAO),
                new GetAllDevelopersByDepartment(view, developerDAO, skillDAO),
                new GetAllDevelopersByLevel(view, developerDAO, skillDAO)
        );
    }

    public void run() {

        view.write("Welcome");
        while (true) {
            view.write("\nChoose a command. Press Q to exit\n");
            commands.forEach(x -> view.write(x.command()));
            String input = view.read();
            if (input.equalsIgnoreCase("Q"))
                break;
            doCommand(input);
        }
    }

    private void doCommand(String input) {

        for (Command command : commands)
            try {
                if (command.canProcess(input)) {
                    command.process();
                    break;
                }
            } catch (Exception e) {
                view.write(e.getMessage());
            }
    }


}

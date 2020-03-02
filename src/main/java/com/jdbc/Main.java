package com.jdbc;

import com.jdbc.config.DatabaseManagerConnector;
import com.jdbc.service.CompanyService;
import com.jdbc.service.CustomerService;
import com.jdbc.service.DeveloperService;
import com.jdbc.service.ProjectService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        DatabaseManagerConnector dbConnector = new DatabaseManagerConnector("localhost", 5432,
                "it");
        Connection connection = dbConnector.getConnection();

        ProjectService projectService = new ProjectService(connection);
        CompanyService companyService = new CompanyService(connection);
        CustomerService customerService = new CustomerService(connection);
        DeveloperService developerService = new DeveloperService(connection);

    }
}
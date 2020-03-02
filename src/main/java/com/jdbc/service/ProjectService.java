package com.jdbc.service;

import com.jdbc.dao.CompanyDAO;
import com.jdbc.dao.CustomerDAO;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Customer;
import com.jdbc.model.Project;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProjectService {

    private ProjectDAO projectDAO;
    private CustomerDAO customerDAO;
    private CompanyDAO companyDAO;

    public ProjectService(Connection connection) {
        projectDAO = new ProjectDAO(connection);
        customerDAO = new CustomerDAO(connection);
        companyDAO = new CompanyDAO(connection);
    }

    public ProjectDAO getProjectDAO() {
        return projectDAO;
    }

    public void createProject() {

        Scanner scanner = new Scanner(System.in);

        Project project = new Project();
        String projectName;
        String status;
        int cost;
        Date date;

        System.out.println("Enter a project title");
        projectName = scanner.nextLine();
        System.out.println("Enter a project status");
        status = scanner.nextLine();
        System.out.println("Enter a project date");
        date = Date.valueOf(scanner.nextLine());
        System.out.println("Enter a project cost");
        cost = scanner.nextInt();

        project.setProjectName(projectName);
        project.setStatus(status);
        project.setCost(cost);
        project.setDate(date);

        projectDAO.create(project);
        linkProject(project);
    }

    public void deleteProject(int id) {
        projectDAO.remove(id);
    }

    public Project getProject(int id) {
        return projectDAO.getByID(id);
    }

    public List<Project> getAllProjects() {
        return projectDAO.getAll();
    }

    private void linkProject(Project project) {

        Scanner scanner = new Scanner(System.in);

        int projectID;
        int customerID;
        int companyID;
        String projectName = project.getProjectName();
        String customerName;
        String companyName;

        projectID = getAllProjects()
                .stream()
                .filter(x -> x.getProjectName().equals(projectName))
                .map(Project::getProjectID)
                .collect(Collectors.toList())
                .get(0);

        System.out.println("Choose customer id");
        customerDAO.getAll().forEach(x -> System.out.printf("%d. %s \n", x.getCustomerID(), x.getCustomerName()));
        customerID = scanner.nextInt();
        customerName = customerDAO.getByID(customerID).getCustomerName();

        System.out.println("Choose company id");
        companyDAO.getAll().forEach(x -> System.out.printf("%d. %s \n", x.getCompanyID(), x.getCompanyName()));
        companyID = scanner.nextInt();
        companyName = companyDAO.getByID(companyID).getCompanyName();

        projectDAO.linkCustomerProject(customerID, projectID);
        projectDAO.linkCompanyProject(companyID, projectID);

        System.out.printf("Project %s by customer %s will be developing by company %s", projectName, customerName, companyName);
    }
}

package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CustomerDAO;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Customer;
import com.jdbc.model.Project;

import java.util.List;
import java.util.stream.Collectors;

public class LinkProjectToCustomer implements Command {

    private View view;
    private ProjectDAO projectDAO;
    private CustomerDAO customerDAO;

    public LinkProjectToCustomer(View view, ProjectDAO projectDAO, CustomerDAO customerDAO) {
        this.view = view;
        this.projectDAO = projectDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public String command() {
        return "Link project to customer";
    }

    @Override
    public void process() {

        List<Project> projectsList = projectDAO.getAll();
        List<Integer> projectsIDList = projectsList
                .stream()
                .map(Project::getProjectID)
                .collect(Collectors.toList());
        int projectID;

        List<Customer> customersList = customerDAO.getAll();
        List<Integer> customersIDList = customersList
                .stream()
                .map(Customer::getCustomerID)
                .collect(Collectors.toList());
        int customerID;

        do {
            view.write("Choose project id");
            projectsList.forEach(System.out::println);
            projectID = Integer.parseInt(view.read());
        } while (!matchInt(projectID, projectsIDList));

        do {
            view.write("Chose customer id");
            customersList.forEach(System.out::println);
            customerID = Integer.parseInt(view.read());
        } while (!matchInt(customerID, customersIDList));

        projectDAO.linkCustomerProject(customerID, projectID);

        String projectTitle = projectDAO.getByID(projectID).getProjectName();
        String customerName = customerDAO.getByID(customerID).getCustomerName();

        view.redWrite(String.format("Project %s by customer %s \n", projectTitle, customerName));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

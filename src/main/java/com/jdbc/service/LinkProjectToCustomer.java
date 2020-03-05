package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CustomerDAO;
import com.jdbc.dao.ProjectDAO;

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

        view.write("Choose project id");
        projectDAO.getAll().forEach(System.out::println);
        int projectID = Integer.parseInt(view.read());

        view.write("Chose customer id");
        customerDAO.getAll().forEach(System.out::println);
        int customerID = Integer.parseInt(view.read());

        projectDAO.linkCustomerProject(customerID, projectID);

        String projectTitle = projectDAO.getByID(projectID).getProjectName();
        String customerName = customerDAO.getByID(customerID).getCustomerName();

        System.err.printf("Project %s by customer %s \n", projectTitle, customerName);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

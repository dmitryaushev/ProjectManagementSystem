package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CompanyDAO;
import com.jdbc.dao.ProjectDAO;

public class LinkProjectToCompany implements Command {

    private View view;
    private ProjectDAO projectDAO;
    private CompanyDAO companyDAO;

    public LinkProjectToCompany(View view, ProjectDAO projectDAO, CompanyDAO companyDAO) {
        this.view = view;
        this.projectDAO = projectDAO;
        this.companyDAO = companyDAO;
    }

    @Override
    public String command() {
        return "Link project to developer";
    }

    @Override
    public void process() {

        view.write("Choose project id");
        projectDAO.getAll().forEach(System.out::println);
        int projectID = Integer.parseInt(view.read());

        view.write("Choose company id");
        companyDAO.getAll().forEach(System.out::println);
        int companyID = Integer.parseInt(view.read());

        projectDAO.linkCompanyProject(companyID, projectID);

        String projectTitle = projectDAO.getByID(projectID).getProjectName();
        String companyName = companyDAO.getByID(companyID).getCompanyName();

        System.err.printf("Project %s will be develop by company %s \n", projectTitle, companyName);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

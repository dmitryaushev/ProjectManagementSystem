package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CompanyDAO;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Company;
import com.jdbc.model.Project;

import java.util.List;
import java.util.stream.Collectors;

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
        return "Link project to company";
    }

    @Override
    public void process() {

        view.write("Enter a project id");
        int projectID = Integer.parseInt(view.read());
        Project project = projectDAO.getByID(projectID);

        if (project == null)
            throw new IllegalArgumentException(String.format("Project with id %d not exist", projectID));

        view.write("Enter a company id");
        int companyID = Integer.parseInt(view.read());
        Company company = companyDAO.getByID(companyID);

        if (company == null)
            throw new IllegalArgumentException(String.format("Company with id %d not exist", companyID));

        String projectName = projectDAO.getByID(projectID).getProjectName();
        String companyName = companyDAO.getByID(companyID).getCompanyName();

        if (projectDAO.checkCompanyProjectLink(companyID, projectID))
            throw new UnsupportedOperationException(String.format(
                    "Project %s already developing by company %s", projectName, companyName
            ));

        view.write(String.format("Connect a project %s with a company %s? Y|N", projectName, companyName));
        switch (view.read()) {
            case "Y":
                break;
            case "N":
                return;
            default:
                throw new IllegalArgumentException("Wrong input");
        }

        projectDAO.linkCompanyProject(companyID, projectID);

        view.write("Successful");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
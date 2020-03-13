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

        List<Project> projectsList = projectDAO.getAll();
        List<Integer> projectsIDList = projectsList
                .stream()
                .map(Project::getProjectID)
                .collect(Collectors.toList());
        int projectID;

        List<Company> companiesList = companyDAO.getAll();
        List<Integer> companiesIDList = companiesList
                .stream()
                .map(Company::getCompanyID)
                .collect(Collectors.toList());
        int companyID;

        do {
            view.write("Choose project id");
            projectsList.forEach(System.out::println);
            projectID = Integer.parseInt(view.read());
        } while (!matchInt(projectID, projectsIDList));

        do {
            view.write("Choose company id");
            companiesList.forEach(System.out::println);
            companyID = Integer.parseInt(view.read());
        } while (!matchInt(companyID, companiesIDList));

        projectDAO.linkCompanyProject(companyID, projectID);

        String projectTitle = projectDAO.getByID(projectID).getProjectName();
        String companyName = companyDAO.getByID(companyID).getCompanyName();

        view.redWrite(String.format("Project %s will be develop by company %s \n", projectTitle, companyName));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
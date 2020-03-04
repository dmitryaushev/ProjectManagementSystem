package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;
import com.jdbc.dao.ProjectDAO;

public class LinkDeveloperToProject implements Command {

    private View view;
    private DeveloperDAO developerDAO;
    private ProjectDAO projectDAO;

    public LinkDeveloperToProject(View view, DeveloperDAO developerDAO, ProjectDAO projectDAO) {
        this.view = view;
        this.developerDAO = developerDAO;
        this.projectDAO = projectDAO;
    }

    @Override
    public String command() {
        return "Link developer to project";
    }

    @Override
    public void process() {

        view.write("Choose developer id");
        developerDAO.getAll().forEach(System.out::println);
        int developerID = Integer.parseInt(view.read());

        view.write("Choose project id");
        projectDAO.getAll().forEach(System.out::println);
        int projectID = Integer.parseInt(view.read());

        developerDAO.linkDeveloperProject(developerID, projectID);

        String firstName = developerDAO.getByID(developerID).getFirstName();
        String lastName = developerDAO.getByID(developerID).getLastName();
        String title = projectDAO.getByID(projectID).getProjectName();

        System.err.printf("Developer %s %s will be develop a project %s \n", firstName, lastName, title);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

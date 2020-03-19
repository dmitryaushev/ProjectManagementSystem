package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Developer;
import com.jdbc.model.Project;

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

        view.write("Enter a developer id");
        int developerID = Integer.parseInt(view.read());
        Developer developer = developerDAO.getByID(developerID);

        if (developer == null)
            throw new IllegalArgumentException(String.format("Developer with id %d not exist", developerID));

        view.write("Enter a project id");
        int projectID = Integer.parseInt(view.read());
        Project project = projectDAO.getByID(projectID);

        if (project == null)
            throw new IllegalArgumentException(String.format("Project with id %d not exist", projectID));

        String firstName = developer.getFirstName();
        String lastName = developer.getLastName();
        String projectName = project.getProjectName();

        if (developerDAO.checkDeveloperProjectLink(developerID, projectID))
            throw new UnsupportedOperationException(String.format(
                    "Developer %s %s already developing a project %s", firstName, lastName, projectName
            ));

        view.write(String.format("Connect %s %s with a project %s? Y|N", firstName, lastName, projectName));
        switch (view.read()) {
            case "Y":
                break;
            case "N":
                return;
            default:
                throw new IllegalArgumentException("Wrong input");
        }

        developerDAO.linkDeveloperProject(developerID, projectID);

        view.write("Successful");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

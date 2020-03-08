package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Developer;
import com.jdbc.model.Project;

import java.util.List;
import java.util.stream.Collectors;

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

        List<Developer> developersList = developerDAO.getAll();
        List<Integer> developersIDList = developersList
                .stream()
                .map(Developer::getDeveloperID)
                .collect(Collectors.toList());
        int developerID;

        List<Project> projectsList = projectDAO.getAll();
        List<Integer> projectsIDList = projectsList
                .stream()
                .map(Project::getProjectID)
                .collect(Collectors.toList());
        int projectID;

        do {
            view.write("Choose developer id");
            developersList.forEach(System.out::println);
            developerID = Integer.parseInt(view.read());
        } while (!matchInt(developerID, developersIDList));

        do {
            view.write("Choose project id");
            projectsList.forEach(System.out::println);
            projectID = Integer.parseInt(view.read());
        } while (!matchInt(projectID, projectsIDList));

        developerDAO.linkDeveloperProject(developerID, projectID);

        String firstName = developerDAO.getByID(developerID).getFirstName();
        String lastName = developerDAO.getByID(developerID).getLastName();
        String title = projectDAO.getByID(projectID).getProjectName();

        view.redWrite(String.format("Developer %s %s will be develop a project %s \n", firstName, lastName, title));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

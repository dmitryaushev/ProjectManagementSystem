package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Project;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllDevelopersByProject implements Command {

    private View view;
    private ProjectDAO projectDAO;

    public GetAllDevelopersByProject(View view, ProjectDAO projectDAO) {
        this.view = view;
        this.projectDAO = projectDAO;
    }

    @Override
    public String command() {
        return "Get all developers by project";
    }

    @Override
    public void process() {

        List<Project> projectsList = projectDAO.getAll();
        List<Integer> idList = projectsList
                .stream()
                .map(Project::getProjectID)
                .collect(Collectors.toList());
        int projectID;

        do {
            view.write("Choose project id");
            projectsList.forEach(System.out::println);
            projectID = Integer.parseInt(view.read());
        } while (!matchInt(projectID, idList));

        projectDAO.getAllDevelopersByProject(projectID).forEach(System.err::println);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

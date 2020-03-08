package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Project;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateProject implements Command {

    private View view;
    private ProjectDAO projectDAO;

    public UpdateProject(View view, ProjectDAO projectDAO) {
        this.view = view;
        this.projectDAO = projectDAO;
    }

    @Override
    public String command() {
        return "Update project";
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

        view.write("Enter a project title");
        String title = view.read();
        view.write("Enter a project status");
        String status = view.read();
        view.write("Enter a project date");
        Date date = Date.valueOf(view.read());
        view.write("Enter a project cost");
        int cost = Integer.parseInt(view.read());

        Project project = new Project();
        project.setProjectID(projectID);
        project.setProjectName(title);
        project.setStatus(status);
        project.setDate(date);
        project.setCost(cost);
        projectDAO.update(project);

        System.err.println("Project updated");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

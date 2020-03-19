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

        view.write("Enter a project id");
        int projectID = Integer.parseInt(view.read());
        Project project = projectDAO.getByID(projectID);

        if (project == null)
            throw new IllegalArgumentException(String.format("Project with id %d not exist", projectID));

        view.write("Update project? Y|N");
        view.write(project.toString());
        switch (view.read()) {
            case "Y":
                break;
            case "N":
                return;
            default:
                throw new IllegalArgumentException("Wrong input");
        }

        view.write("Enter a project title");
        String title = view.read();
        view.write("Enter a project status");
        String status = view.read();
        view.write("Enter a project date");
        Date date = Date.valueOf(view.read());
        view.write("Enter a project cost");
        int cost = Integer.parseInt(view.read());

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

package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Project;

import java.sql.Date;

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

        view.write("Choose project id");
        projectDAO.getAll().forEach(System.out::println);
        int projectID = Integer.parseInt(view.read());
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

        view.redWrite("Project updated");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

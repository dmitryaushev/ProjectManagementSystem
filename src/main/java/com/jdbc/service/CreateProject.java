package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Project;

import java.sql.Date;

public class CreateProject implements Command {

    private View view;
    private ProjectDAO projectDAO;

    public CreateProject(View view, ProjectDAO projectDAO) {
        this.view = view;
        this.projectDAO = projectDAO;
    }

    @Override
    public String command() {
        return "Create project";
    }

    @Override
    public void process() {

        view.write("Enter a project title");
        String title = view.read();
        view.write("Enter a project status");
        String status = view.read();
        view.write("Enter a project date");
        Date date = Date.valueOf(view.read());
        view.write("Enter a project cost");
        int cost = Integer.parseInt(view.read());

        Project project = new Project();
        project.setProjectName(title);
        project.setStatus(status);
        project.setDate(date);
        project.setCost(cost);
        projectDAO.create(project);
        view.write("Project created");
        sleep();
    }
}

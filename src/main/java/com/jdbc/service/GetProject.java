package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Project;

import java.util.List;
import java.util.stream.Collectors;

public class GetProject implements Command {

    private View view;
    private ProjectDAO projectDAO;

    public GetProject(View view, ProjectDAO projectDAO) {
        this.view = view;
        this.projectDAO = projectDAO;
    }

    @Override
    public String command() {
        return "Get project";
    }

    @Override
    public void process() {

        view.write("Enter a project id");
        int projectID = Integer.parseInt(view.read());

        if (projectDAO.getByID(projectID) == null)
            throw new IllegalArgumentException(String.format("Project with id %d not exist", projectID));

        view.redWrite(projectDAO.getByID(projectID).toString());
        sleep();
    }
}

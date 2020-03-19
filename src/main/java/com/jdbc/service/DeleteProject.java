package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Project;

public class DeleteProject implements Command {

    private View view;
    private ProjectDAO projectDAO;

    public DeleteProject(View view, ProjectDAO projectDAO) {
        this.view = view;
        this.projectDAO = projectDAO;
    }

    @Override
    public String command() {
        return "Delete project";
    }

    @Override
    public void process() {

        view.write("Enter a project id");
        int projectID = Integer.parseInt(view.read());
        Project project = projectDAO.getByID(projectID);

        if (project == null)
            throw new IllegalArgumentException(String.format("Project with id %d not exist", projectID));

        view.write("Delete project? Y|N");
        view.write(project.toString());
        question(view.read());

        projectDAO.unlinkCustomerProject(projectID);
        projectDAO.unlinkCompanyProject(projectID);
        projectDAO.unlinkDeveloperProject(projectID);
        projectDAO.delete(projectID);
        view.redWrite("Project deleted");
        sleep();
    }
}

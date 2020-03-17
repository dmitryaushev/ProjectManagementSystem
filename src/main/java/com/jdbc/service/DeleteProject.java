package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.ProjectDAO;

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
        int projectID = Integer.parseInt(view.read());;

        if (projectDAO.getByID(projectID) == null)
            throw new IllegalArgumentException(String.format("Project with id %d not exist", projectID));

        projectDAO.unlinkCustomerProject(projectID);
        projectDAO.unlinkCompanyProject(projectID);
        projectDAO.unlinkDeveloperProject(projectID);

        projectDAO.remove(projectID);
        view.redWrite("Project deleted");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

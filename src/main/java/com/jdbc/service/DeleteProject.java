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

        view.write("Choose project id");
        projectDAO.getAll().forEach(System.out::println);
        int id = Integer.parseInt(view.read());
        projectDAO.remove(id);
        view.redWrite("Project deleted");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

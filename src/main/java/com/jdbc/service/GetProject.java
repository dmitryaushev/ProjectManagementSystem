package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.ProjectDAO;

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

        view.write("Choose project id");
        projectDAO.getAll().forEach(x -> System.out.println(x.getProjectID()));
        int id =Integer.parseInt(view.read());
        System.out.println(projectDAO.getByID(id).toString());
    }
}

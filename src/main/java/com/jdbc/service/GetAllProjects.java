package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.dao.ProjectDAO;

public class GetAllProjects implements Command {

    private ProjectDAO projectDAO;

    public GetAllProjects(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Override
    public String command() {
        return "Get all projects";
    }

    @Override
    public void process() {

        projectDAO.getAll().forEach(System.err::println);
        sleep();
    }
}

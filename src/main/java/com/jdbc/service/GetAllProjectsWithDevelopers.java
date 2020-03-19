package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.dao.ProjectDAO;

public class GetAllProjectsWithDevelopers implements Command {

    private ProjectDAO projectDAO;

    public GetAllProjectsWithDevelopers(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Override
    public String command() {
        return "Get all projects with developers";
    }

    @Override
    public void process() {

        projectDAO.getAllProjectsWithDevelopers().forEach(System.out::println);
        sleep();
    }
}

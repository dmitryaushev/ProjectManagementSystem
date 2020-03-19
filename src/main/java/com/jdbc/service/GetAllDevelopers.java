package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.dao.DeveloperDAO;

public class GetAllDevelopers implements Command {

    private DeveloperDAO developerDAO;

    public GetAllDevelopers(DeveloperDAO developerDAO) {
        this.developerDAO = developerDAO;
    }

    @Override
    public String command() {
        return "Get all developers";
    }

    @Override
    public void process() {

        developerDAO.getAll().forEach(System.err::println);
        sleep();
    }
}

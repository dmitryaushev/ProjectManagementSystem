package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;

public class DeleteDeveloper implements Command {

    private View view;
    private DeveloperDAO developerDAO;

    public DeleteDeveloper(View view, DeveloperDAO developerDAO) {
        this.view = view;
        this.developerDAO = developerDAO;
    }

    @Override
    public String command() {
        return "Delete Developer";
    }

    @Override
    public void process() {

        view.write("Choose developer id");
        developerDAO.getAll().forEach(x -> System.out.println(x.getDeveloperID()));
        int id = Integer.parseInt(view.read());
        developerDAO.remove(id);
    }
}

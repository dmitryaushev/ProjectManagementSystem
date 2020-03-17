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
        return "Delete developer";
    }

    @Override
    public void process() {

        view.write("Enter a developer id");
        int developerID = Integer.parseInt(view.read());

        if (developerDAO.getByID(developerID) == null)
            throw new IllegalArgumentException(String.format("Developer with id %d not exist", developerID));

        developerDAO.unlinkDeveloperProject(developerID);
        developerDAO.remove(developerID);
        view.redWrite("Developer deleted");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;

public class GetDeveloper implements Command {

    private View view;
    private DeveloperDAO developerDAO;

    public GetDeveloper(View view, DeveloperDAO developerDAO) {
        this.view = view;
        this.developerDAO = developerDAO;
    }

    @Override
    public String command() {
        return "Get developer";
    }

    @Override
    public void process() {

        view.write("Choose developer id");
        developerDAO.getAll().forEach(x -> System.out.println(x.getDeveloperID()));
        int id = Integer.parseInt(view.read());
        view.redWrite(developerDAO.getByID(id).toString());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

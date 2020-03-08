package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;
import com.jdbc.model.Developer;

import java.util.List;
import java.util.stream.Collectors;

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

        List<Developer> developersList = developerDAO.getAll();
        List<Integer> idList = developersList
                .stream()
                .map(Developer::getDeveloperID)
                .collect(Collectors.toList());
        int developerID;

        do {
            view.write("Choose developer id");
            developersList.forEach(System.out::println);
            developerID = Integer.parseInt(view.read());
        } while (!matchInt(developerID, idList));

        developerDAO.remove(developerID);
        view.redWrite("Developer deleted");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

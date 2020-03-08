package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;
import com.jdbc.model.Developer;

import java.util.List;
import java.util.stream.Collectors;

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

        List<Integer> idList = developerDAO.getAll()
                .stream()
                .map(Developer::getDeveloperID)
                .collect(Collectors.toList());
        int developerID;

        do {
            view.write("Choose developer id");
            idList.forEach(System.out::println);
            developerID = Integer.parseInt(view.read());
        } while (!matchInt(developerID, idList));

        view.redWrite(developerDAO.getByID(developerID).toString());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

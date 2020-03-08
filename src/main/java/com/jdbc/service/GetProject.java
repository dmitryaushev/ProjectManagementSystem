package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.model.Project;

import java.util.List;
import java.util.stream.Collectors;

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

        List<Integer> idList = projectDAO.getAll()
                .stream()
                .map(Project::getProjectID)
                .collect(Collectors.toList());
        int projectID;

        do {
            view.write("Choose project id");
            idList.forEach(System.out::println);
            projectID = Integer.parseInt(view.read());
        } while (!matchInt(projectID, idList));

        view.redWrite(projectDAO.getByID(projectID).toString());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

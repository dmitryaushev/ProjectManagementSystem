package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.ProjectDAO;

public class GetSumSalaryByProject implements Command {

    private View view;
    private ProjectDAO projectDAO;

    public GetSumSalaryByProject(View view, ProjectDAO projectDAO) {
        this.view = view;
        this.projectDAO = projectDAO;
    }

    @Override
    public String command() {
        return "Get sum salary by project";
    }

    @Override
    public void process() {

        view.write("Choose project id");
        projectDAO.getAll().forEach(System.out::println);
        int projectID = Integer.parseInt(view.read());

        int sum = projectDAO.getSumSalary(projectID);
        String projectTitle = projectDAO.getByID(projectID).getProjectName();

        view.redWrite(String.format("Salary of all developers in project %s is %d\n", projectTitle, sum));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

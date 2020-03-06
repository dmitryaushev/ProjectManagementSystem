package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;
import com.jdbc.model.Developer;

public class UpdateDeveloper implements Command {

    private View view;
    private DeveloperDAO developerDAO;

    public UpdateDeveloper(View view, DeveloperDAO developerDAO) {
        this.view = view;
        this.developerDAO = developerDAO;
    }

    @Override
    public String command() {
        return "Update developer";
    }

    @Override
    public void process() {

        view.write("Choose developer id");
        developerDAO.getAll().forEach(System.out::println);
        int developerID = Integer.parseInt(view.read());
        view.write("Enter developer first name");
        String firstName = view.read();
        view.write("Enter developer last name");
        String lastName = view.read();
        view.write("Enter developer gender");
        String gender = view.read();
        view.write("Enter developer age");
        int age = Integer.parseInt(view.read());
        view.write("Enter developer salary");
        int salary = Integer.parseInt(view.read());

        Developer developer = new Developer();
        developer.setDeveloperID(developerID);
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setGender(gender);
        developer.setAge(age);
        developer.setSalary(salary);
        developerDAO.update(developer);

        view.redWrite("Developer updated");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

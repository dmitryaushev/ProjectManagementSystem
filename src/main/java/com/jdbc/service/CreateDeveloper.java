package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;
import com.jdbc.model.Developer;

public class CreateDeveloper implements Command {

    private View view;
    private DeveloperDAO developerDAO;

    public CreateDeveloper(View view, DeveloperDAO developerDAO) {
        this.view = view;
        this.developerDAO = developerDAO;
    }

    @Override
    public String command() {
        return "Create developer";
    }

    @Override
    public void process() {

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
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setGender(gender);
        developer.setAge(age);
        developer.setSalary(salary);
        developerDAO.create(developer);

        view.redWrite("Developer created");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

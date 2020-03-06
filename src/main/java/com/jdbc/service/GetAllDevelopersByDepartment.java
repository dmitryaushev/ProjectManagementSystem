package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;
import com.jdbc.dao.SkillDAO;
import com.jdbc.model.Skill;

import java.util.stream.Collectors;

public class GetAllDevelopersByDepartment implements Command {

    private View view;
    private DeveloperDAO developerDAO;
    private SkillDAO skillDAO;

    public GetAllDevelopersByDepartment(View view, DeveloperDAO developerDAO, SkillDAO skillDAO) {
        this.view = view;
        this.developerDAO = developerDAO;
        this.skillDAO = skillDAO;
    }

    @Override
    public String command() {
        return "Get all developers by department";
    }

    @Override
    public void process() {

        view.write("Choose skill department");
        skillDAO.getAll()
                .stream()
                .map(Skill::getDepartment)
                .collect(Collectors.toSet())
                .forEach(System.out::println);
        String department = view.read();

        developerDAO.getAllDevelopersByDepartment(department).forEach(System.err::println);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

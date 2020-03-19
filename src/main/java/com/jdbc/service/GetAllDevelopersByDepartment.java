package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;
import com.jdbc.dao.SkillDAO;
import com.jdbc.model.Skill;

import java.util.Set;
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

        Set<String> departmentsSet = skillDAO.getAll()
                .stream()
                .map(Skill::getDepartment)
                .collect(Collectors.toSet());
        String department;

        do {
            view.write("Choose skill department");
            departmentsSet.forEach(System.out::println);
            department = view.read();
        }while (!matchString(department, departmentsSet));

        developerDAO.getAllDevelopersByDepartment(department).forEach(System.err::println);
        sleep();
    }
}

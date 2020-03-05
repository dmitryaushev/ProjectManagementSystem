package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.DeveloperDAO;
import com.jdbc.dao.SkillDAO;
import com.jdbc.model.Skill;

import java.util.stream.Collectors;

public class GetAllDevelopersByLevel implements Command {

    private View view;
    private DeveloperDAO developerDAO;
    private SkillDAO skillDAO;

    public GetAllDevelopersByLevel(View view, DeveloperDAO developerDAO, SkillDAO skillDAO) {
        this.view = view;
        this.developerDAO = developerDAO;
        this.skillDAO = skillDAO;
    }

    @Override
    public String command() {
        return "Get all developers by level";
    }

    @Override
    public void process() {

        view.write("Choose skill level");
        skillDAO.getAll()
                .stream()
                .map(Skill::getLevel)
                .collect(Collectors.toSet())
                .forEach(System.out::println);
        String level = view.read();

        developerDAO.getAllDevelopersByLevel(level).forEach(System.out::println);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

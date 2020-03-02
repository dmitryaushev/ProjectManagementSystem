package com.jdbc.dao;

import com.jdbc.model.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillDAO {

    private Connection connection;

    private static final String SELECT = "SELECT * FROM skills;";

    public SkillDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Skill> getAll() {

        List<Skill> skillsList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Skill skill = new Skill();
                skill.setSkillID(resultSet.getInt("skill_id"));
                skill.setDepartment(resultSet.getString("department"));
                skill.setLevel(resultSet.getString("level"));

                skillsList.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return skillsList;
    }
}

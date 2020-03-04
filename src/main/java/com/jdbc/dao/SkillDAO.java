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

    private static final String SELECT_ALL = "SELECT * FROM skills;";
    private static final String SELECT = "SELECT * FROM skills WHERE skill_id = ?;";

    public SkillDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Skill> getAll() {

        List<Skill> skillsList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {

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

    public Skill getByID(int id) {

        Skill skill = new Skill();

        try (PreparedStatement statement = connection.prepareStatement(SELECT)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                skill.setSkillID(resultSet.getInt("skill_id"));
                skill.setDepartment(resultSet.getString("department"));
                skill.setLevel(resultSet.getString("level"));
            } else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return skill;
    }
}

package com.jdbc.dao;

import com.jdbc.config.DataAccessObject;
import com.jdbc.model.Developer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperDAO extends DataAccessObject<Developer> {

    private Connection connection;

    private static final String INSERT = "INSERT INTO developers(developer_id, first_name, last_name, gender, age, salary) " +
                                         "VALUES(?, ?, ?, ?, ?, ?);";
    private static final String SELECT = "SELECT * FROM developers WHERE developer_id = ?";
    private static final String SELECT_ALL = "SELECT * FROM developers;";
    private static final String DELETE = "DELETE FROM developers WHERE developer_id = ?;";
    private static final String GET_ALL_DEVELOPER_BY_DEPARTMENT = "SELECT d.developer_id, d.first_name, d.last_name, d.gender, d.age, d.salary\n" +
                                                                  "FROM developers d\n" +
                                                                  "JOIN developer_skill ds ON d.developer_id = ds.developer_id\n" +
                                                                  "JOIN skills s ON ds.skill_id = s.skill_id\n" +
                                                                  "WHERE s.department = ?;";
    private static final String GET_ALL_DEVELOPER_BY_LEVEL = "SELECT d.developer_id, d.first_name, d.last_name, d.gender, d.age, d.salary\n" +
                                                             "FROM developers d\n" +
                                                             "JOIN developer_skill ds ON d.developer_id = ds.developer_id\n" +
                                                             "JOIN skills s ON ds.skill_id = s.skill_id\n" +
                                                             "WHERE s.level = ?;";

    public DeveloperDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Developer developer) {

        try(PreparedStatement statement = connection.prepareStatement(INSERT)) {

            statement.setInt(1, developer.getDeveloperID());
            statement.setString(2, developer.getFirstName());
            statement.setString(3, developer.getLastName());
            statement.setString(4, developer.getGender());
            statement.setInt(5, developer.getAge());
            statement.setInt(6, developer.getSalary());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Developer getByID(int id) {

        Developer developer = new Developer();

        try (PreparedStatement statement = connection.prepareStatement(SELECT)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                developer.setDeveloperID(id);
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                developer.setGender(resultSet.getString("gender"));
                developer.setAge(resultSet.getInt("age"));
                developer.setSalary(resultSet.getInt("salary"));
            } else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public List<Developer> getAll() {

        List<Developer> developersList = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Developer developer = new Developer();
                developer.setDeveloperID(resultSet.getInt("developer_id"));
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                developer.setGender(resultSet.getString("gender"));
                developer.setAge(resultSet.getInt("age"));
                developer.setSalary(resultSet.getInt("salary"));

                developersList.add(developer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developersList;
    }

    @Override
    public void update(Developer object) {

    }

    @Override
    public void remove(int id) {

        try(PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Developer> getAllDeveloperByDepartment(String department) {

        List<Developer> developersList = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_DEVELOPER_BY_DEPARTMENT)) {
            statement.setString(1, department);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Developer developer = new Developer();
                developer.setDeveloperID(resultSet.getInt("developer_id"));
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                developer.setGender(resultSet.getString("gender"));
                developer.setAge(resultSet.getInt("age"));
                developer.setSalary(resultSet.getInt("salary"));

                developersList.add(developer);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return developersList;
    }

    public List<Developer> getAllDeveloperByLevel(String level) {

        List<Developer> developersList = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_DEVELOPER_BY_LEVEL)) {
            statement.setString(1, level);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Developer developer = new Developer();
                developer.setDeveloperID(resultSet.getInt("developer_id"));
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                developer.setGender(resultSet.getString("gender"));
                developer.setAge(resultSet.getInt("age"));
                developer.setSalary(resultSet.getInt("salary"));

                developersList.add(developer);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return developersList;
    }
}

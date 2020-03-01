package com.jdbc.dao;

import com.jdbc.config.DataAccessObject;
import com.jdbc.model.Developer;
import com.jdbc.model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends DataAccessObject<Project> {

    private Connection connection;

    private static final String INSERT = "INSERT INTO projects(project_id, project_name, status, cost, date) " +
                                         "VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT = "SELECT * FROM projects WHERE project_id = ?;";
    private static final String SELECT_ALL = "SELECT * FROM projects;";
    private static final String DELETE = "DELETE FROM projects WHERE project_id = ?;";

    private static String getSumSalaryByProject;
    private static String getAllDevelopersByProject;
    private static String getAllProjectsWithDevelopers;

    public ProjectDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Project project) {

        try (PreparedStatement statement = connection.prepareStatement(INSERT)){

            statement.setInt(1, project.getProjectID());
            statement.setString(2, project.getProjectName());
            statement.setString(3, project.getStatus());
            statement.setInt(4, project.getCost());
            statement.setDate(5, project.getDate());
            statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Project getByID(int id) {

        Project project = new Project();

        try (PreparedStatement statement = connection.prepareStatement(SELECT)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                project.setProjectID(id);
                project.setProjectName(resultSet.getString("project_name"));
                project.setStatus(resultSet.getString("status"));
                project.setCost(resultSet.getInt("cost"));
                project.setDate(resultSet.getDate("date"));
            } else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public List<Project> getAll() {

        List<Project> projectsList = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Project project = new Project();
                project.setProjectID(resultSet.getInt("project_id"));
                project.setProjectName(resultSet.getString("project_name"));
                project.setStatus(resultSet.getString("status"));
                project.setCost(resultSet.getInt("cost"));
                project.setDate(resultSet.getDate("date"));

                projectsList.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectsList;
    }

    @Override
    public void update(Project object) {

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

    public int getSumSalary(Project project) {

        getSumSalaryByProject =
                "SELECT sum(salary)\n" +
                "FROM developers d\n" +
                "JOIN developer_project dp ON d.developer_id = dp.developer_id\n" +
                "JOIN projects p ON dp.project_id = p.project_id\n" +
                "WHERE p.project_name = ?;";
        int sum = 0;

        try (PreparedStatement statement = connection.prepareStatement(getSumSalaryByProject)) {
            statement.setString(1, project.getProjectName());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
                sum = resultSet.getInt("sum");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    public List<Developer> getAllDevelopers (Project project) {

        getAllDevelopersByProject =
                "SELECT d.developer_id, d.first_name, d.last_name, d.gender, d.age, d.salary\n" +
                "FROM developers d\n" +
                "JOIN developer_project dp ON d.developer_id = dp.developer_id\n" +
                "JOIN projects p ON dp.project_id = p.project_id\n" +
                "WHERE p.project_id = ?;";
        List<Developer> developersList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(getAllDevelopersByProject)) {
            statement.setInt(1, project.getProjectID());

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

    public List<String> getALLProjectsWithDevelopers() {

        getAllProjectsWithDevelopers =
                "SELECT p.date, p.project_name, count(d) AS amount\n" +
                "FROM projects p \n" +
                "JOIN developer_project dp ON p.project_id = dp.project_id\n" +
                "JOIN developers d ON dp.developer_id = d.developer_id\n" +
                "GROUP BY p.date, p.project_name\n" +
                "ORDER BY amount DESC;";
        List<String> projectsList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(getAllProjectsWithDevelopers)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Date date = resultSet.getDate("date");
                String name = resultSet.getString("project_name");
                int amount = resultSet.getInt("amount");

                String s = String.format("%s %s %d", date, name, amount);
                projectsList.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projectsList;
    }

}

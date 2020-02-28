package com.jdbc.dao;

import com.jdbc.config.DataAccessObject;
import com.jdbc.model.Developer;
import com.jdbc.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends DataAccessObject<Project> {

    private Connection connection;

    private static final String INSERT = "INSERT INTO projects(project_id, project_name, status, cost, date) " +
                                         "VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT = "SELECT * FROM projects WHERE project_id = ?;";
    private static final String SELECT_ALL = "SELECT * FROM projects;";
    private static final String DELETE = "DELETE FROM projects WHERE project_id = ?;";
    private static final String GET_SUM_SALARY = "select sum(salary)\n" +
                                                 "from developers d\n" +
                                                 "join developer_project dp ON d.developer_id = dp.developer_id\n" +
                                                 "join projects p on dp.project_id = p.project_id\n" +
                                                 "where p.project_name = ?;";
    private static final String GET_ALL_DEVELOPERS = "select d.developer_id, d.first_name, d.last_name, d.gender, d.age, d.salary\n" +
                                                     "from developers d\n" +
                                                     "join developer_project dp ON d.developer_id = dp.developer_id\n" +
                                                     "join projects p on dp.project_id = p.project_id\n" +
                                                     "where p.project_id = ?;";

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

        int sum = 0;

        try (PreparedStatement statement = connection.prepareStatement(GET_SUM_SALARY)) {
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

        List<Developer> developersList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_DEVELOPERS)) {
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


}

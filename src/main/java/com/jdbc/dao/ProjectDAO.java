package com.jdbc.dao;

import com.jdbc.config.DataAccessObject;
import com.jdbc.model.Developer;
import com.jdbc.model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends DataAccessObject<Project> {

    private Connection connection;

    private static final String INSERT = "INSERT INTO projects(project_name, status, cost, date) " +
                                         "VALUES (?, ?, ?, ?);";
    private static final String SELECT = "SELECT * FROM projects WHERE project_id = ?;";
    private static final String SELECT_ALL = "SELECT * FROM projects;";
    private static final String UPDATE =
            "UPDATE projects " +
            "SET project_name = ?, status = ?, cost = ?, date = ? " +
            "WHERE project_id = ?;";
    private static final String DELETE = "DELETE FROM projects WHERE project_id = ?;";

    private static String getSumSalaryByProject;
    private static String getAllDevelopersByProject;
    private static String getAllProjectsWithDevelopers;

    private static String linkCustomerProject = "INSERT INTO customer_project (customer_id, project_id) " +
                                                "VALUES(?, ?)";
    private static String linkCompanyProject = "INSERT INTO company_project (company_id, project_id) " +
                                               "VALUES(?, ?)";

    private static String unlinkCustomerProject = "DELETE FROM customer_project WHERE project_id = ?;";
    private static String unlinkCompanyProject = "DELETE FROM company_project WHERE project_id = ?;";
    private static String unlinkDeveloperProject = "DELETE FROM developer_project WHERE project_id = ?;";
    private static String getCompanyProjectLink = "SELECT * FROM company_project " +
                                                  "WHERE company_id = ? AND project_id = ?;";
    private static String getCustomerProjectLink = "SELECT * FROM customer_project " +
                                                   "WHERE customer_id = ? AND project_id = ?;";

    public ProjectDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Project project) {

        try (PreparedStatement statement = connection.prepareStatement(INSERT)){

            statement.setString(1, project.getProjectName());
            statement.setString(2, project.getStatus());
            statement.setInt(3, project.getCost());
            statement.setDate(4, project.getDate());
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
    public void update(Project project) {

        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            statement.setString(1, project.getProjectName());
            statement.setString(2, project.getStatus());
            statement.setInt(3, project.getCost());
            statement.setDate(4, project.getDate());
            statement.setInt(5, project.getProjectID());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {

        try(PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSumSalary(int id) {

        getSumSalaryByProject =
                "SELECT sum(salary)\n" +
                "FROM developers d\n" +
                "JOIN developer_project dp ON d.developer_id = dp.developer_id\n" +
                "JOIN projects p ON dp.project_id = p.project_id\n" +
                "WHERE p.project_id = ?;";
        int sum = 0;

        try (PreparedStatement statement = connection.prepareStatement(getSumSalaryByProject)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
                sum = resultSet.getInt("sum");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    public List<Developer> getAllDevelopersByProject(int id) {

        getAllDevelopersByProject =
                "SELECT d.developer_id, d.first_name, d.last_name, d.gender, d.age, d.salary\n" +
                "FROM developers d\n" +
                "JOIN developer_project dp ON d.developer_id = dp.developer_id\n" +
                "JOIN projects p ON dp.project_id = p.project_id\n" +
                "WHERE p.project_id = ?;";

        List<Developer> developersList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(getAllDevelopersByProject)) {
            statement.setInt(1, id);

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

    public List<String> getAllProjectsWithDevelopers() {

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

    public void linkCustomerProject(int customerID, int projectID) {

        try (PreparedStatement statement = connection.prepareStatement(linkCustomerProject)) {

            statement.setInt(1, customerID);
            statement.setInt(2, projectID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void linkCompanyProject(int companyID, int projectID) {

        try (PreparedStatement statement = connection.prepareStatement(linkCompanyProject)) {

            statement.setInt(1, companyID);
            statement.setInt(2, projectID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unlinkCustomerProject(int projectID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkCustomerProject)) {

            statement.setInt(1, projectID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unlinkCompanyProject(int projectID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkCompanyProject)) {

            statement.setInt(1, projectID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unlinkDeveloperProject(int projectID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkDeveloperProject)) {

            statement.setInt(1, projectID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkCustomerProjectLink(int customerID, int projectID) {

        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(getCustomerProjectLink)) {
            statement.setInt(1, customerID);
            statement.setInt(2, projectID);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkCompanyProjectLink(int companyID, int projectID) {

        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(getCompanyProjectLink)) {
            statement.setInt(1, companyID);
            statement.setInt(2, projectID);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}

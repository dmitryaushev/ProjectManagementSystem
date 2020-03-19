package com.jdbc.dao;

import com.jdbc.config.DataAccessObject;
import com.jdbc.model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO extends DataAccessObject<Company> {

    private Connection connection;

    private static final String INSERT = "INSERT INTO companies(company_name, location) " +
            "VALUES (?, ?);";
    private static final String SELECT = "SELECT * FROM companies WHERE company_id = ?;";
    private static final String SELECT_ALL = "SELECT * FROM companies;";
    private static final String UPDATE = "UPDATE companies SET company_name = ?, location = ? WHERE company_id = ?;";
    private static final String DELETE = "DELETE FROM companies WHERE company_id = ?;";

    private static String unlinkCompanyProject = "DELETE FROM company_project WHERE company_id = ?;";

    public CompanyDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Company company) {

        try (PreparedStatement statement = connection.prepareStatement(INSERT)){

            statement.setString(1, company.getCompanyName());
            statement.setString(2, company.getLocation());
            statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Company getByID(int id) {

        Company company = new Company();

        try (PreparedStatement statement = connection.prepareStatement(SELECT)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                company.setCompanyID(id);
                company.setCompanyName(resultSet.getString("company_name"));
                company.setLocation(resultSet.getString("location"));
            } else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public List<Company> getAll() {

        List<Company> companiesList = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Company company = new Company();
                company.setCompanyID(resultSet.getInt("company_id"));
                company.setCompanyName(resultSet.getString("company_name"));
                company.setLocation(resultSet.getString("location"));

                companiesList.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companiesList;
    }

    @Override
    public void update(Company company) {

        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            statement.setString(1, company.getCompanyName());
            statement.setString(2, company.getLocation());
            statement.setInt(3, company.getCompanyID());
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

    public void unlinkCompanyProject(int companyID) {

        try (PreparedStatement statement = connection.prepareStatement(unlinkCompanyProject)) {

            statement.setInt(1, companyID);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

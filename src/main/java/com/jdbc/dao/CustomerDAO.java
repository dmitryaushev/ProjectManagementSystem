package com.jdbc.dao;

import com.jdbc.config.DataAccessObject;
import com.jdbc.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends DataAccessObject<Customer> {

    private Connection connection;

    private static final String INSERT = "INSERT INTO customers(customer_name, location) " +
            "VALUES (?, ?);";
    private static final String SELECT = "SELECT * FROM customers WHERE customer_id = ?;";
    private static final String SELECT_ALL = "SELECT * FROM customers;";
    private static final String UPDATE = "UPDATE customers SET customer_name = ?, location = ? WHERE customer_id = ?;";
    private static final String DELETE = "DELETE FROM customers WHERE customer_id = ?;";

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Customer customer) {

        try (PreparedStatement statement = connection.prepareStatement(INSERT)){

            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getLocation());
            statement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Customer getByID(int id) {

        Customer customer = new Customer();

        try (PreparedStatement statement = connection.prepareStatement(SELECT)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                customer.setCustomerID(id);
                customer.setCustomerName(resultSet.getString("customer_name"));
                customer.setLocation(resultSet.getString("location"));
            } else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public List<Customer> getAll() {

        List<Customer> customersList = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Customer customer = new Customer();
                customer.setCustomerID(resultSet.getInt("customer_id"));
                customer.setCustomerName(resultSet.getString("customer_name"));
                customer.setLocation(resultSet.getString("location"));

                customersList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersList;
    }

    @Override
    public void update(Customer customer) {

        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getLocation());
            statement.setInt(3, customer.getCustomerID());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}

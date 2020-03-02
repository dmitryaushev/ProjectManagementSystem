package com.jdbc.service;

import com.jdbc.dao.CustomerDAO;
import com.jdbc.model.Customer;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class CustomerService {

    CustomerDAO customerDAO;

    public CustomerService(Connection connection) {
        customerDAO = new CustomerDAO(connection);
    }

    public void createCustomer() {

        Scanner scanner = new Scanner(System.in);

        Customer customer = new Customer();
        String customerName;
        String location;

        System.out.println("Enter a customer name");
        customerName = scanner.nextLine();
        System.out.println("Enter a customer location");
        location = scanner.nextLine();

        customer.setCustomerName(customerName);
        customer.setLocation(location);

        customerDAO.create(customer);
    }

    public void deleteCustomer(int id) {
        customerDAO.remove(id);
    }

    public Customer getCustomer(int id) {
        return customerDAO.getByID(id);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAll();
    }
}

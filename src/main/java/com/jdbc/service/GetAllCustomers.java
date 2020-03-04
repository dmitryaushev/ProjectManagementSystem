package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.dao.CustomerDAO;

public class GetAllCustomers implements Command {

    private CustomerDAO customerDAO;

    public GetAllCustomers(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public String command() {
        return "Get all customers";
    }

    @Override
    public void process() {

        customerDAO.getAll().forEach(System.out::println);
    }
}

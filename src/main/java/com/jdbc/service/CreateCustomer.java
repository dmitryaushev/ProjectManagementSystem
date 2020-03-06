package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CustomerDAO;
import com.jdbc.model.Customer;

public class CreateCustomer implements Command {

    private View view;
    private CustomerDAO customerDAO;

    public CreateCustomer(View view, CustomerDAO customerDAO) {
        this.view = view;
        this.customerDAO = customerDAO;
    }

    @Override
    public String command() {
        return "Create customer";
    }

    @Override
    public void process() {

        view.write("Enter a customer name");
        String name = view.read();
        view.write("Enter a customer location");
        String location = view.read();

        Customer customer = new Customer();
        customer.setCustomerName(name);
        customer.setLocation(location);
        customerDAO.create(customer);

        view.redWrite("Customer created");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

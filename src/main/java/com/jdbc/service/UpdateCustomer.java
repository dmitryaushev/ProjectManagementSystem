package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CustomerDAO;
import com.jdbc.model.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class UpdateCustomer implements Command {

    private View view;
    private CustomerDAO customerDAO;

    public UpdateCustomer(View view, CustomerDAO customerDAO) {
        this.view = view;
        this.customerDAO = customerDAO;
    }

    @Override
    public String command() {
        return "Update customer";
    }

    @Override
    public void process() {

        view.write("Enter a customer id");
        int customerID = Integer.parseInt(view.read());
        Customer customer = customerDAO.getByID(customerID);

        if (customer == null)
            throw new IllegalArgumentException(String.format("Customer with id %d not exist", customerID));

        view.write("Update customer? Y|N");
        view.write(customer.toString());
        question(view.read());

        view.write("Enter a customer name");
        String customerName = view.read();
        view.write("Enter a customer location");
        String location = view.read();

        customer.setCustomerID(customerID);
        customer.setCustomerName(customerName);
        customer.setLocation(location);
        customerDAO.update(customer);
        view.redWrite("Customer updated");
        sleep();
    }
}

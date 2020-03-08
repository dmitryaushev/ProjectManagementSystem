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

        List<Customer> customersList = customerDAO.getAll();
        List<Integer> idList = customersList
                .stream()
                .map(Customer::getCustomerID)
                .collect(Collectors.toList());
        int customerID;

        do {
            view.write("Choose customer id");
            customersList.forEach(System.out::println);
            customerID = Integer.parseInt(view.read());
        } while (!matchInt(customerID, idList));

        view.write("Enter a customer name");
        String customerName = view.read();
        view.write("Enter a customer location");
        String location = view.read();

        Customer customer = new Customer();
        customer.setCustomerID(customerID);
        customer.setCustomerName(customerName);
        customer.setLocation(location);

        customerDAO.update(customer);
        view.redWrite("Customer updated");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CustomerDAO;
import com.jdbc.model.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteCustomer implements Command {

    private View view;
    private CustomerDAO customerDAO;

    public DeleteCustomer(View view, CustomerDAO customerDAO) {
        this.view = view;
        this.customerDAO = customerDAO;
    }

    @Override
    public String command() {
        return "Delete customer";
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

        customerDAO.unlinkCustomerProject(customerID);
        customerDAO.remove(customerID);
        view.redWrite("Customer deleted");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
